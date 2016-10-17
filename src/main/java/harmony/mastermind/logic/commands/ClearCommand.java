package harmony.mastermind.logic.commands;

import harmony.mastermind.commons.exceptions.CommandCanceledException;
import harmony.mastermind.model.TaskManager;

/**
 * Clears the task manager. Calling this will wipe data, undo history and redo history
 */
public class ClearCommand extends Command {

    public static final String COMMAND_WORD = "clear";
    public static final String MESSAGE_SUCCESS = "Mastermind has been cleared!";
    public static final String MESSAGE_FAIL = "No changes made.";

    public static final String COMMAND_SUMMARY = "Clearing current tab's tasks:"
            + "\n" + COMMAND_WORD;

    public ClearCommand() {}


    @Override
    public CommandResult execute() {
        assert model != null;
        try {
            confirmRequest();
        } catch (CommandCanceledException cce) {
            return new CommandResult(MESSAGE_FAIL);
        }
        model.resetData(TaskManager.getEmptyTaskManager());
        return new CommandResult(MESSAGE_SUCCESS);
    }


    private void confirmRequest() throws CommandCanceledException {
        // TODO Auto-generated method stub
        
    }
}
