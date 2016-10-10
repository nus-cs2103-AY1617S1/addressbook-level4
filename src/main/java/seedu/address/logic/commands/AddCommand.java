package seedu.address.logic.commands;

import seedu.address.commons.exceptions.IllegalValueException;
import seedu.address.model.ToDoList;
import seedu.address.model.tag.Tag;
import seedu.address.model.tag.UniqueTagList;
import seedu.address.model.task.Detail;
import seedu.address.model.task.Done;
import seedu.address.model.task.DueByDate;
import seedu.address.model.task.DueByTime;
import seedu.address.model.task.Priority;
import seedu.address.model.task.ReadOnlyTask;
import seedu.address.model.task.Task;
import seedu.address.model.task.UniqueTaskList;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.HashSet;
import java.util.Set;

/**
 * Adds a person to the address book.
 */
public class AddCommand extends Command {

    public static final String COMMAND_WORD = "add";

    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Adds a task to the address book. "
            + "Parameters: <details> by/on <date> at <time> /<priority> /<TAG...>\n"
            + "Example: " + COMMAND_WORD
            + " bring dog to the vet on Thursday at noon /high -dog";

    public static final String MESSAGE_SUCCESS = "New task added: %1$s";
    public static final String MESSAGE_DUPLICATE_TASK = "This task already exists in the address book";

    private final Task toAdd;

    /**
     * Convenience constructor using raw values.
     *
     * @throws IllegalValueException if any of the raw values are invalid
     */
    public AddCommand(String details,
                      LocalDate dueByDate,
                      LocalTime dueByTime,
                      String priority,
                      Set<String> tags) throws IllegalValueException {
        final Set<Tag> tagSet = new HashSet<>();
        for (String tagName : tags) {
            tagSet.add(new Tag(tagName));
        }
        this.toAdd = new Task(
                new Detail(details),
                new DueByDate (dueByDate),
                new DueByTime(dueByTime),
                new Priority(priority),
                new UniqueTagList(tagSet)
        );
    }

    public AddCommand(Task toAdd) {
        this.toAdd = toAdd;
    }

    public ReadOnlyTask getTask() {
        return toAdd;
    }

    @Override
    public CommandResult execute() {
        try {
            model.addTask(toAdd);
            return new CommandResult(String.format(MESSAGE_SUCCESS, toAdd));
        } catch (UniqueTaskList.DuplicateTaskException dpe) {
            return new CommandResult(MESSAGE_DUPLICATE_TASK);
        }
    }

}