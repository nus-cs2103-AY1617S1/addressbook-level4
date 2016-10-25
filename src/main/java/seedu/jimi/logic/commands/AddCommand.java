package seedu.jimi.logic.commands;

import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import seedu.jimi.commons.exceptions.IllegalValueException;
import seedu.jimi.model.datetime.DateTime;
import seedu.jimi.model.event.Event;
import seedu.jimi.model.tag.Priority;
import seedu.jimi.model.tag.Tag;
import seedu.jimi.model.tag.UniqueTagList;
import seedu.jimi.model.task.DeadlineTask;
import seedu.jimi.model.task.FloatingTask;
import seedu.jimi.model.task.Name;
import seedu.jimi.model.task.ReadOnlyTask;
import seedu.jimi.model.task.UniqueTaskList;

/**
 * Adds a task to the Jimi.
 */
public class AddCommand extends Command implements TaskBookEditor {

    public static final String COMMAND_WORD = "add";

    public static final String MESSAGE_USAGE = 
            COMMAND_WORD + ": Adds a task or event to Jimi with one optional tag.\n"
            + "\n"
            + "To add a task:\n"
            + "Parameters: \"TASK_DETAILS\" [due DATE_TIME] [t/TAG]\n"
            + "Example: " + COMMAND_WORD + " \"do dishes\" t/important\n"
            + "\n"
            + "To add an event:\n"
            + "Parameters: \"TASK_DETAILS\" on START_DATE_TIME [to END_DATE_TIME] [t/TAG]\n"
            + "Example: " + COMMAND_WORD + " \"linkin park concert\" on sunday 2pm t/fun\n"
            + "\n"
            + "> Tip: Typing 'a' or 'ad' instead of 'add' works too.\n";

    public static final String MESSAGE_SUCCESS = "New task added: %1$s";
    public static final String MESSAGE_DUPLICATE_TASK = "This task already exists in Jimi";

    private final ReadOnlyTask toAdd;
    
    public AddCommand() {
        toAdd = null;
    }
    
    /**
     * Convenience constructor using raw values to add tasks.
     *
     * @throws IllegalValueException if any of the raw values are invalid
     */
    public AddCommand(String name, List<Date> dates, Set<String> tags, String priority) throws IllegalValueException {
        final Set<Tag> tagSet = new HashSet<>();
        for (String tagName : tags) {
            tagSet.add(new Tag(tagName));
        }
        if (dates.size() == 0) {
            this.toAdd = new FloatingTask(new Name(name), new UniqueTagList(tagSet), new Priority(priority));
        } else {
            this.toAdd = new DeadlineTask(new Name(name), new DateTime(dates.get(0)), new UniqueTagList(tagSet), new Priority(priority));
        }
    }
    
    /**
     * Convenience constructor using raw values to add events.
     * 
     * @throws IllegalValueException
     */
    public AddCommand(String name, List<Date> startDateTime, List<Date> endDateTime, Set<String> tags, String priority)
            throws IllegalValueException {
        final Set<Tag> tagSet = new HashSet<>();
        for (String tagName : tags) {
            tagSet.add(new Tag(tagName));
        }
        
        // events do not always have an end date 
        DateTime endDateTimeToAdd = endDateTime.isEmpty() ? null : new DateTime(endDateTime.get(0));
                
        this.toAdd = new Event(
                new Name(name), 
                new DateTime(startDateTime.get(0)), 
                endDateTimeToAdd,
                new UniqueTagList(tagSet),
                new Priority(priority)
        );
    }
    
    @Override
    public CommandResult execute() {
        assert model != null;
        try {
            model.addTask(toAdd);
            return new CommandResult(String.format(MESSAGE_SUCCESS, toAdd));
        } catch (UniqueTaskList.DuplicateTaskException e) {
            return new CommandResult(MESSAGE_DUPLICATE_TASK);
        }
        
    }
    
    @Override
    public boolean isValidCommandWord(String commandWord) {
        for (int i = 1; i <= COMMAND_WORD.length(); i++) {
            if (commandWord.equals(COMMAND_WORD.substring(0, i))) {
                return true;
            }
        }
        return false;
    }
}
