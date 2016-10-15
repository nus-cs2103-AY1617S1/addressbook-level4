package seedu.jimi.logic.commands;

import java.util.HashSet;
import java.util.Set;

import seedu.jimi.commons.exceptions.IllegalValueException;
import seedu.jimi.model.datetime.DateTime;
import seedu.jimi.model.event.Event;
import seedu.jimi.model.tag.Tag;
import seedu.jimi.model.tag.UniqueTagList;
import seedu.jimi.model.task.*;

/**
 * Adds a task to the Jimi.
 */
public class AddCommand extends Command {

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
            + "> Tip: typing `a` or `ad` instead of `add` works too.";

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
    public AddCommand(String name, String dateTime, Set<String> tags) throws IllegalValueException {
        final Set<Tag> tagSet = new HashSet<>();
        for (String tagName : tags) {
            tagSet.add(new Tag(tagName));
        }
        if (dateTime == null) {
            this.toAdd = new FloatingTask(new Name(name), new UniqueTagList(tagSet));
        } else {
            this.toAdd = new DeadlineTask(new Name(name), new DateTime(dateTime), new UniqueTagList(tagSet));
        }
    }
    
    /**
     * Convenience constructor using raw values to add events.
     * 
     * @throws IllegalValueException
     */
    public AddCommand(String name, String startDateTime, String endDateTime, Set<String> tags)
            throws IllegalValueException {
        final Set<Tag> tagSet = new HashSet<>();
        for (String tagName : tags) {
            tagSet.add(new Tag(tagName));
        }
        
        this.toAdd = new Event(new Name(name), new DateTime(startDateTime), new DateTime(endDateTime),
                new UniqueTagList(tagSet));
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
