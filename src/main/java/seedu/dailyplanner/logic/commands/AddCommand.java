package seedu.dailyplanner.logic.commands;

import java.util.HashSet;
import java.util.Set;

import seedu.dailyplanner.commons.exceptions.IllegalValueException;
import seedu.dailyplanner.model.tag.Tag;
import seedu.dailyplanner.model.tag.UniqueTagList;
import seedu.dailyplanner.model.task.*;
import seedu.dailyplanner.history.*;

/**
 * Adds a person to the address book.
 */
public class AddCommand extends Command {

    public static final String COMMAND_WORD = "add";

    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Adds a task to the daily planner. "
            + "Parameters: NAME d/DATE s/STARTTIME e/ENDTIME [c/CATEGORY]...\n"
            + "Example: " + COMMAND_WORD
            + " CS2103 Assignment d/11/11/2016 s/10pm e/11pm c/urgent c/important";

    public static final String MESSAGE_SUCCESS = "New task added: %1$s";
    public static final String MESSAGE_DUPLICATE_PERSON = "This task already exists in the daily planner";

    private final Task toAdd;

    /**
     * Convenience constructor using raw values.
     * @param endDate 
     *
     * @throws IllegalValueException if any of the raw values are invalid
     */
    public AddCommand(String taskName, String date, String endDate, String startTime, String endTime, Set<String> tags)
            throws IllegalValueException {
        final Set<Tag> tagSet = new HashSet<>();
        for (String tagName : tags) {
            tagSet.add(new Tag(tagName));
        }
        this.toAdd = new Task(
                new Name(taskName),
                new Date(date, endDate),
                new StartTime(startTime),
                new EndTime(endTime),
                new UniqueTagList(tagSet),
                "NOT COMPLETE"
        );
    }
    

    @Override
    public CommandResult execute() {
        assert model != null;
        try {
        	model.getHistory().stackDeleteInstruction(toAdd);
            model.addPerson(toAdd);
            return new CommandResult(String.format(MESSAGE_SUCCESS, toAdd));
        } catch (UniqueTaskList.DuplicatePersonException e) {
            return new CommandResult(MESSAGE_DUPLICATE_PERSON);
        }

    }
    
}
