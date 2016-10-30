package harmony.mastermind.logic.commands;

import harmony.mastermind.commons.exceptions.CommandCancelledException;
import harmony.mastermind.model.TaskManager;

//@@author A0139194X
/**
 * Clears the task manager.
 */
public class ClearCommand extends Command  {

    public static final String COMMAND_WORD = "clear";
    public static final String MESSAGE_SUCCESS = "Mastermind has been cleared!";
    public static final String COMMAND_SUMMARY = "Clearing current tab's tasks:"
            + "\n" + COMMAND_WORD;

    public ClearCommand() {}

    @Override
    public CommandResult execute() {
        assert model != null;
        model.resetData(TaskManager.getEmptyTaskManager());
        
        return new CommandResult(COMMAND_WORD, MESSAGE_SUCCESS);
    }
}
