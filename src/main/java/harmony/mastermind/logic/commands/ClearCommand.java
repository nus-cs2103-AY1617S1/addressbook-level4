package harmony.mastermind.logic.commands;

import harmony.mastermind.commons.exceptions.CommandCancelledException;
import harmony.mastermind.model.TaskManager;

/**
 * Clears the task manager.
 */
public class ClearCommand extends Command {

    public static final String COMMAND_WORD = "clear";
    public static final String MESSAGE_SUCCESS = "Mastermind has been cleared!";
    public static final String COMMAND_SUMMARY = "Clearing current tab's tasks:"
            + "\n" + COMMAND_WORD;

    public ClearCommand() {}


    @Override
    public CommandResult execute() {
        assert model != null;
        try {
            confirmWithUser();
        } catch (CommandCancelledException cce) {
            
        }
        
        model.resetData(TaskManager.getEmptyTaskManager());
        return new CommandResult(MESSAGE_SUCCESS);
    }


    private void confirmWithUser() throws CommandCancelledException {
        // TODO Auto-generated method stub
        
    }
}
