package seedu.menion.logic.commands;

import seedu.menion.model.ActivityManager;
import seedu.menion.model.ReadOnlyActivityManager;

/**
 * Clears the activity manager.
 */
public class ClearCommand extends Command {

    public static final String COMMAND_WORD = "clear";
    public static final String MESSAGE_SUCCESS = "Menion has been cleared!";
    
    public ClearCommand() {}


    @Override
    public CommandResult execute() {
        assert model != null;
        
    	storePreviousState();
        
        model.resetData(ActivityManager.getEmptyActivityManager());
        return new CommandResult(MESSAGE_SUCCESS);
    }
    
    //@@author A0139515A
    /**
     * Clear command will store previous activity manager to support undo command
     * 
     */
    public void storePreviousState() {
        assert model != null;

        ReadOnlyActivityManager beforeState = new ActivityManager(model.getActivityManager());
    	model.addStateToUndoStack(beforeState);
    }
    //@@author
}
