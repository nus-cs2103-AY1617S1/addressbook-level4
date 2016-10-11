package seedu.address.logic.commands.taskcommands;

import seedu.address.commons.collections.UniqueItemCollection;
import seedu.address.commons.exceptions.IllegalValueException;
import seedu.address.logic.commands.CommandResult;
import seedu.address.model.task.*;

/**
 * Adds a task to TaskManager.
 */
public class AddTaskCommand extends TaskCommand {

    public static final String COMMAND_WORD = "add";

    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Adds a task to TaskManager. "
            + "Parameters: DESCRIPTION \n"
            + "Example: " + COMMAND_WORD
            + " Finish V0.1";

    public static final String MESSAGE_SUCCESS = "New task added: %1$s";
    public static final String MESSAGE_DUPLICATE_TASK = "This task already exists in TaskManager";
    public static final String MESSAGE_EMPTY_TASK = "Description to AddTaskCommand constructor is empty.\n";

    private final Task toAdd;

    /**
     * Convenience constructor using raw values.
     *
     * @throws IllegalValueException if any of the raw values are invalid
     */
    public AddTaskCommand(String description)
            throws IllegalValueException {
    	if (description == null || description.isEmpty()) {
    		throw new IllegalValueException(MESSAGE_EMPTY_TASK + MESSAGE_USAGE);
    	}
        this.toAdd = new FloatingTask(new Description(description));
    }

    @Override
    public CommandResult execute() {
        assert model != null;
        try {
            model.addTask(toAdd);
            model.clearTasksFilter();
            return new CommandResult(String.format(MESSAGE_SUCCESS, toAdd));
        } catch (UniqueItemCollection.DuplicateItemException e) {
            return new CommandResult(MESSAGE_DUPLICATE_TASK);
        }

    }

}
