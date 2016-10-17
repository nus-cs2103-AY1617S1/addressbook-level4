package seedu.menion.logic.commands;

import seedu.menion.model.ActivityManager;
import seedu.menion.model.ReadOnlyActivityManager;

/**
 * Clears the activity manager.
 */
public class ClearCommand extends Command {

    public static final String COMMAND_WORD = "clear";
    public static final String MESSAGE_SUCCESS = "Menion has been cleared!";
    private ReadOnlyActivityManager beforeClear;
    
    public ClearCommand() {}


    @Override
    public CommandResult execute() {
        assert model != null;
        
    	this.beforeClear = new ActivityManager(model.getActivityManager());
        model.resetData(ActivityManager.getEmptyActivityManager());
        return new CommandResult(MESSAGE_SUCCESS);
    }

    /*
     * revert the Activity Manager to the state before it was cleared
     */
	@Override
	public boolean undo() {
		assert model != null;
        
        model.resetData(beforeClear);
        return true;
	}
}
