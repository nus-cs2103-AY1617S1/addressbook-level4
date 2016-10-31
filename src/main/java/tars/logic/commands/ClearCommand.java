package tars.logic.commands;

import tars.model.Tars;

/**
 * Clears tars.
 * 
 * @@author A0139924W
 */
public class ClearCommand extends Command {

    public static final String COMMAND_WORD = "clear";
    public static final String MESSAGE_SUCCESS = "TARS has been cleared!";

    @Override
    public CommandResult execute() {
        assert model != null;
        model.resetData(Tars.getEmptyTars());
        
        model.getUndoableCmdHist().clear();
        model.getRedoableCmdHist().clear();
        
        return new CommandResult(MESSAGE_SUCCESS);
    }
}
