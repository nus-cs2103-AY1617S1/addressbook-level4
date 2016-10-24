package seedu.menion.logic.commands;

import seedu.menion.model.ActivityManager;

//@@author A0139515A
/**
 * Revert to previous activity manager state before undo command is called
 *
 */
public class RedoCommand extends Command {

    public static final String COMMAND_WORD = "redo";
    public static final String MESSAGE_SUCCESS = "Menion successfully redo your previous changes";
    public static final String MESSAGE_FAILURE = "Menion is unable to redo to your previous changes";

    public RedoCommand() {}


    @Override
    public CommandResult execute() {
        assert model != null;
        boolean ableToRedo = redo();
        if (ableToRedo) {
        	return new CommandResult(MESSAGE_SUCCESS);
        }
        
        return new CommandResult(MESSAGE_FAILURE);
    }

    /**
     * Return true if able revert to previous activity manager, otherwise return false.
     */
	public boolean redo() {
		assert model != null;
		
		if (model.checkStatesInRedoStack()) {
			return false;
		}
		
		model.addStateToUndoStack(new ActivityManager(model.getActivityManager()));
		model.resetData(model.retrievePreviousStateFromRedoStack());
		
		return true;
	}
}