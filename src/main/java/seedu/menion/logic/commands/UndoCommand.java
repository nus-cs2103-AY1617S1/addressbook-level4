package seedu.menion.logic.commands;

import seedu.menion.model.ActivityManager;

//@@author A0139515A
/**
 * Revert to previous activity manager state.
 *
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
        
        return new CommandResult(MESSAGE_FAILURE);
    }

    /**
     * Return true if able revert to previous activity manager, otherwise return false.
     */
	public boolean undo() {
		assert model != null;
		
		if (model.checkStatesInUndoStack()) {
			return false;
		}
		
		model.addStateToRedoStack(new ActivityManager(model.getActivityManager()));
		model.resetData(model.retrievePreviousStateFromUndoStack());
		
		return true;
	}
}