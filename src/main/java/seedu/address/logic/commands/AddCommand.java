package seedu.address.logic.commands;

import seedu.address.commons.exceptions.IllegalValueException;
import seedu.address.model.task.Name;
import seedu.address.model.task.Task;

/**
 * Adds a person to the task book.
 */
public class AddCommand extends Command {

    public static final String COMMAND_WORD = "add";

    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Adds a task to the task book. "
            + "Parameters: NAME\n"
            + "Example: " + COMMAND_WORD
            + " John Doe";

    public static final String MESSAGE_SUCCESS = "New task added: %1$s";

    private final Task toAdd;

    /**
     * Convenience constructor using raw values.
     *
     * @throws IllegalValueException if any of the raw values are invalid
     */
    public AddCommand(String name) throws IllegalValueException {
        this.toAdd = new Task(new Name(name));
    }

    @Override
    public CommandResult execute() {
        assert model != null;
        model.addTask(toAdd);
        return new CommandResult(String.format(MESSAGE_SUCCESS, toAdd));
    }

}
