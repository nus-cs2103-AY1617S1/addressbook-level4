package seedu.task.logic.commands;

import seedu.task.model.TaskManager;

/**
 * Clears the address book.
 */
public class ClearCommand extends Command {

    public static final String COMMAND_WORD = "clear";
    public static final String MESSAGE_SUCCESS = "Task manager has been cleared!";

    public ClearCommand() {}


    @Override
    public CommandResult execute() {
        assert model != null;
        model.resetData(TaskManager.getEmptyTaskManager());
        return new CommandResult(MESSAGE_SUCCESS);
    }
}
