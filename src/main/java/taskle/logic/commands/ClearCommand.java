package taskle.logic.commands;

import taskle.model.TaskManager;

/**
 * Clears the task manager.
 */
public class ClearCommand extends Command {

    public static final String COMMAND_WORD = "clear";
    public static final String MESSAGE_SUCCESS = "Task Manager has been cleared!";

    public ClearCommand() {}


    @Override
    public CommandResult execute() {
        assert model != null;
        model.resetData(TaskManager.getEmptyTaskManager());
        return new CommandResult(MESSAGE_SUCCESS);
    }
}
