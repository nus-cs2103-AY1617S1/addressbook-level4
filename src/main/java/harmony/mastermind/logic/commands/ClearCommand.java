package harmony.mastermind.logic.commands;

import harmony.mastermind.model.TaskManager;

//@@author A0139194X
/**
 * Clears the task manager by reseting the data and clearing both undo and redo history
 */
public class ClearCommand extends Command  {

    public static final String COMMAND_WORD = "clear";
    public static final String MESSAGE_SUCCESS = "Mastermind has been cleared!";
    public static final String COMMAND_DESCRIPTION = "Clearing all of Mastermind's data";

    public ClearCommand() {}

    @Override
    public CommandResult execute() {
        assert model != null;
        model.resetData(TaskManager.getEmptyTaskManager());
        
        return new CommandResult(COMMAND_WORD, MESSAGE_SUCCESS);
    }
}
