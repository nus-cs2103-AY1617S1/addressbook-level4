package seedu.menion.logic.commands;

import seedu.menion.model.ActivityManager;

/**
 * Revert to previous activity manager state.
 * 
 *  @author Seow Wei Jie A0139515A
 */
public class UndoCommand extends Command {

    public static final String COMMAND_WORD = "undo";
    public static final String MESSAGE_SUCCESS = "Menion successfully undo your previous changes";
    public static final String MESSAGE_FAILURE = "Menion is unable to undo to your previous changes";

    public UndoCommand() {}


    @Override
    public CommandResult execute() {
        assert model != null;
        boolean ableToUndo = undo();
        if (ableToUndo) {
        	return new CommandResult(MESSAGE_SUCCESS);
        }
        else {
        	return new CommandResult(MESSAGE_FAILURE);
        }
    }

    /**
     * Return true if able to previous activity manager, otherwise return false.
     */
	public boolean undo() {
		assert model != null;
		
		if (model.checkPreviousStates()) {
			return false;
		}
		
		model.resetData(model.retrievePreviousState());
		
		return true;
	}
}