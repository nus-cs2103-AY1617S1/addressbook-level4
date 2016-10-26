package seedu.forgetmenot.logic.commands;

import seedu.forgetmenot.model.TaskManager;

/**
 * Clears the task manager.
 */
public class ClearCommand extends Command {

    public static final String COMMAND_WORD = "clear";
    public static final String MESSAGE_SUCCESS = "Task Manager has been cleared!";
    public static final String MESSAGE_USAGE = COMMAND_WORD
    		+ ": Clears the task manager";

    public ClearCommand() {}


    @Override
    public CommandResult execute() {
        assert model != null;
        model.saveToHistory();
        model.resetData(TaskManager.getEmptyTaskManager());
        return new CommandResult(MESSAGE_SUCCESS);
    }
}
