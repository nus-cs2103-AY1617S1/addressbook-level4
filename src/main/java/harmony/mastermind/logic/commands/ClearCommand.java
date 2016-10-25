package harmony.mastermind.logic.commands;

import harmony.mastermind.commons.exceptions.CommandCancelledException;
import harmony.mastermind.model.TaskManager;

/**
 * Clears the task manager.
 */
public class ClearCommand extends Command implements Confirmable {

    public static final String COMMAND_WORD = "clear";
    public static final String MESSAGE_SUCCESS = "Mastermind has been cleared!";
    public static final String COMMAND_SUMMARY = "Clearing current tab's tasks:"
            + "\n" + COMMAND_WORD;
    public static final String MESSAGE_FAIL = "No changes made.";

    public ClearCommand() {}


    //@@author A0139194X
    @Override
    public CommandResult execute() {
        assert model != null;
        //confirmWithUser();
        
        model.resetData(TaskManager.getEmptyTaskManager());
        
        return new CommandResult(COMMAND_WORD,MESSAGE_SUCCESS);
    }

    //@@author A0139194X
    //currently not in used yet. Will be used for enhancement to Clear command.
    @Override
    public void confirmWithUser() throws CommandCancelledException {
        model.indicateConfirmationToUser();
    }
}
