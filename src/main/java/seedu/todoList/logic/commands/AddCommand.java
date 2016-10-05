package seedu.todoList.logic.commands;

import java.util.HashSet;
import java.util.Set;

import seedu.todoList.commons.exceptions.IllegalValueException;
import seedu.todoList.model.tag.Tag;
import seedu.todoList.model.tag.UniqueTagList;
import seedu.todoList.model.task.*;

/**
 * Adds a task to the Todo book.
 */
public class AddCommand extends Command {

    public static final String COMMAND_WORD = "add";

    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Adds a task to the Todo book. "
            + "Parameters: NAME p/PHONE e/EMAIL a/Todo  [t/TAG]...\n"
            + "Example: " + COMMAND_WORD
            + " John Doe p/98765432 e/johnd@gmail.com a/311, Clementi Ave 2, #02-25 t/friends t/owesMoney";

    public static final String MESSAGE_SUCCESS = "New task added: %1$s";
    public static final String MESSAGE_DUPLICATE_TASK = "This task already exists in the Todo list";

    private final Task toAdd;

    /**
     * Convenience constructor using raw values.
     *
     * @throws IllegalValueException if any of the raw values are invalid
     */
    public AddCommand(String name, String startTime, String endTime)
            throws IllegalValueException {
        this.toAdd = new Task(
                new Name(name),
                new StartTime(startTime),
                new EndTime(endTime)
        );
    }

    @Override
    public CommandResult execute() {
        assert model != null;
        try {
            model.addTask(toAdd);
            return new CommandResult(String.format(MESSAGE_SUCCESS, toAdd));
        } catch (UniqueTaskList.DuplicatetaskException e) {
            return new CommandResult(MESSAGE_DUPLICATE_TASK);
        }

    }

}
