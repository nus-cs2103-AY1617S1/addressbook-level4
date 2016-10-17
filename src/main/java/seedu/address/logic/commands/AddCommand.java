package seedu.address.logic.commands;

import seedu.address.commons.exceptions.IllegalValueException;
import seedu.address.model.task.*;
import seedu.address.model.tag.Tag;
import seedu.address.model.tag.UniqueTagList;

import static seedu.address.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.commons.core.Messages.MESSAGE_INVALID_START_AND_END_TIME;

import java.util.HashSet;
import java.util.Set;



/**
 * Adds a task to the task manager.
 */
public class AddCommand extends Command {

    public static final String COMMAND_WORD = "add";

    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Adds a person to the address book. "
            + "Parameters: NAME d/DATE s/START e/ADDRESS  [t/TAG]...\n"
            + "Example: " + COMMAND_WORD
            + " John Doe d/98765432 s/johnd@gmail.com e/311, Clementi Ave 2, #02-25 t/friends t/owesMoney";

    public static final String MESSAGE_SUCCESS = "New task added: %1$s";
    public static final String MESSAGE_DUPLICATE_TASK = "This task already exists in the address book";
    private static final String DEFAULT_DATE = "Thu Jan 01 07:30:00 SGT 1970";

    private final Task toAdd;

    /**
     * Convenience constructor using raw values.
     *
     * @throws IllegalValueException if any of the raw values are invalid
     */
    public AddCommand(String name, String date, String start, String end, Set<String> tags)
            throws IllegalValueException {
    	System.out.println("start");
        final Set<Tag> tagSet = new HashSet<>();
        for (String tagName : tags) {
            tagSet.add(new Tag(tagName));
        }
        
        StartTime startTime = new StartTime(start);
        EndTime endTime = new EndTime(end);
        
        System.out.println(startTime.toString());
        System.out.println(endTime.toString());
        
        if(!checkOrderOfDates(start, end)) {
        	throw new IllegalValueException(MESSAGE_INVALID_START_AND_END_TIME);
        }
        
        this.toAdd = new Task(
                new Name(name),
                new Date(date),
                startTime,
                endTime,
                new UniqueTagList(tagSet)
        );
        
        System.out.println("end");
    }

    @Override
    public CommandResult execute() {
        assert model != null;
        try {
            model.saveToHistory();
            model.addTask(toAdd);
            return new CommandResult(String.format(MESSAGE_SUCCESS, toAdd));
        } catch (UniqueTaskList.DuplicateTaskException e) {
            return new CommandResult(MESSAGE_DUPLICATE_TASK);
        }
	}
    
    /**
     * Checks whether startTime < endTime or not
     * 
     * @param startTime
     * @param endTime
     * @return true only if startTime < endTime
     */
    private boolean checkOrderOfDates(String startTime, String endTime) throws IllegalValueException {
    	StartTime start = new StartTime(startTime);
    	EndTime end = new EndTime(endTime);
    	
    	return end.toString().equalsIgnoreCase(DEFAULT_DATE) || (start.startTime.compareTo(end.endTime) <= 0);

	}

}
