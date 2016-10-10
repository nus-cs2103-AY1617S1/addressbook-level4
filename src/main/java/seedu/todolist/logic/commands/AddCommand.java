package seedu.todolist.logic.commands;

import seedu.todolist.commons.exceptions.IllegalValueException;
import seedu.todolist.model.task.*;

/**
 * Adds a task to the to-do list.
 */
public class AddCommand extends Command {

    public static final String COMMAND_WORD = "add";

    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Adds a task to Task!t. "
            + "Parameters: NAME [at LOCATION]\n"
            + "Example: " + COMMAND_WORD
            + " dinner with mom at home";

    public static final String MESSAGE_SUCCESS = "New task added: %1$s";
    public static final String MESSAGE_DUPLICATE_TASK = "This task already exists in Task!t";

    private final Task toAdd;

    /**
     * Convenience constructor using raw values.
     *
     * @throws IllegalValueException if any of the raw values are invalid
     */
    public AddCommand(String name, String location)
            throws IllegalValueException {
        this.toAdd = new Task(
                new Name(name),
                new LocationParameter(location)
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
