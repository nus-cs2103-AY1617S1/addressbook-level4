package seedu.task.logic.commands;

import seedu.task.commons.exceptions.IllegalValueException;
import seedu.task.model.task.*;
import seedu.task.model.tag.Tag;
import seedu.task.model.tag.UniqueTagList;

import java.text.ParseException;
import java.util.HashSet;
import java.util.Set;

/**
 * Adds a task to the address book.
 */
public class AddCommand extends Command {

    public static final String COMMAND_WORD = "add";

    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Adds a task to the task manager. "
            + "Parameters: TITLE d/DESCRIPTION [sd/START_DATE] [dd/DUE_DATE] i/INTERVAL ti/TIMEINTERVAL [t/TAG]...\n"
            + "Example: " + COMMAND_WORD
            + " HOMEWORK d/math homework dd/11-01-2012 i/123 ti/12";
    public static final String MESSAGE_EVENT_USAGE = "To add an event, DUE_DATE is required\n"
            + "Example: " + COMMAND_WORD + " HOMEWORK d/math homework sd/11-01-2012 dd/11-01-2012 i/123 ti/12";

    public static final String MESSAGE_SUCCESS = "New task added: %1$s";
    public static final String MESSAGE_DUPLICATE_TASK = "This task already exists in the task manager";

    private final Task toAdd;

    /**
     * Convenience constructor using raw values.
     *
     * @throws IllegalValueException if any of the raw values are invalid
     * @throws ParseException 
     */
    public AddCommand(String title, String description, String startDate, String dueDate, String interval, String timeInterval, Set<String> tags)
            throws IllegalValueException, ParseException {
        final Set<Tag> tagSet = new HashSet<>();
        for (String tagName : tags) {
            tagSet.add(new Tag(tagName));
        }
        this.toAdd = new Task(
                new Title(title),
                new Description(description),
                new StartDate(startDate),
                new DueDate(dueDate),
                new Interval(interval),
                new TimeInterval(timeInterval),
                new Status("Ongoing"),
                new UniqueTagList(tagSet)
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

}
