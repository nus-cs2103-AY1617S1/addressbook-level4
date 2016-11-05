package seedu.menion.logic.commands;

import java.io.File;
import java.io.IOException;

import seedu.menion.commons.core.Config;
import seedu.menion.commons.core.EventsCenter;
import seedu.menion.commons.events.storage.StoragePathChangedEvent;
import seedu.menion.commons.events.ui.ModifyStorageEvent;
import seedu.menion.commons.util.ConfigUtil;
import seedu.menion.model.ActivityManager;
import seedu.menion.storage.XmlActivityManagerStorage;

//@@author A0139515A
/**
 * Revert to previous activity manager state before undo command is called
 *
 */
public class RedoCommand extends Command {

    public static final String COMMAND_WORD = "redo";
    public static final String MESSAGE_SUCCESS = "Menion successfully redo your previous changes";
    public static final String MESSAGE_FAILURE = "There are no changes for Menion to redo\n" + 
    											"Examples of redo: \n" +
    											"For normal redo: redo n\n" +
    											"For redo of modify storage path: redo m\n";
    private final String argument;
    
    public RedoCommand(String arugment) {
    	this.argument = arugment.trim();
    }


    @Override
    public CommandResult execute() {
        assert model != null;
        
        model.updateRecentChangedActivity(null);
        
        boolean ableToRedo;
        
        if (argument.equals("n")) {
        	ableToRedo = redoActivityManger();
        }
        else if (argument.equals("m")){
        	ableToRedo = redoStoragePath();
        }
        else {
        	return new CommandResult(MESSAGE_FAILURE);
        }

        if (ableToRedo) {
        	return new CommandResult(MESSAGE_SUCCESS);
        }
        
        return new CommandResult(MESSAGE_FAILURE);
    }

    /**
     * Return true if able revert to previous activity manager, otherwise return false.
     */
	public boolean redoActivityManger() {
		assert model != null;
			
		if (model.checkStatesInRedoStack()) {
			return false;
		}
		
		model.addStateToUndoStack(new ActivityManager(model.getActivityManager()));
		model.resetData(model.retrievePreviousStateFromRedoStack());
		
		return true;
	}
	
	/**
     * Return true if able revert to previous storage path, otherwise return false.
     */
	public boolean redoStoragePath() {
		assert model != null;
		
		if (model.checkStoragePathInRedoStack()) {
			return false;
		}
		
		model.addStoragePathToUndoStack(Config.getInstance().getActivityManagerFilePath());
		
		String storagePathToRedo = model.retrievePreviouStoragePathFromRedoStack();
		
		// Deleting old files
		File oldStorage =  new File(Config.getInstance().getActivityManagerFilePath());
		oldStorage.delete();
		
		// Saving configuration
		Config.getInstance().setActivityManagerFilePath(storagePathToRedo);
		try {
			ConfigUtil.saveConfig(Config.getInstance(), Config.getInstance().DEFAULT_CONFIG_FILE);
		} catch (IOException e) {
			e.printStackTrace();
		}

    	// Setting up new storage location
		XmlActivityManagerStorage newStorage = new XmlActivityManagerStorage(storagePathToRedo);
		EventsCenter.getInstance().post(new StoragePathChangedEvent(newStorage, model.getActivityManager()));		
		
		// Update status bar
		EventsCenter.getInstance().post(new ModifyStorageEvent(storagePathToRedo));
		
		return true;
	}
}