package seedu.menion.logic.commands;

import java.io.File;
import java.io.IOException;
import java.util.Optional;

import seedu.menion.commons.core.Config;
import seedu.menion.commons.core.EventsCenter;
import seedu.menion.commons.events.storage.StoragePathChangedEvent;
import seedu.menion.commons.events.ui.ModifyStorageEvent;
import seedu.menion.commons.exceptions.DataConversionException;
import seedu.menion.commons.util.ConfigUtil;
import seedu.menion.model.ActivityManager;
import seedu.menion.storage.XmlActivityManagerStorage;

//@@author A0139515A
/**
 * Revert to previous activity manager state.
 *
 */
public class UndoCommand extends Command {

    public static final String COMMAND_WORD = "undo";
    public static final String MESSAGE_SUCCESS = "Menion successfully undo your previous changes";
    public static final String MESSAGE_FAILURE = "There are no changes for Menion to undo\n" + 
											"Examples of undo: \n" +
											"For normal undo: undo n\n" +
											"For undo of modify storage path: undo m\n";
    public final String argument;

    public UndoCommand(String argument) {
    	this.argument = argument.trim();
    }


    @Override
    public CommandResult execute() {
        assert model != null;
        
        model.updateRecentChangedActivity(null);
        
        boolean ableToUndo;
        
        if (argument.equals("n")) {
	        ableToUndo = undoActivityManger();
        }
        else if (argument.equals("m")){
        	ableToUndo = undoStoragePath();
        }
        else {
        	return new CommandResult(MESSAGE_FAILURE);
        }
        
        if (ableToUndo) {
        	return new CommandResult(MESSAGE_SUCCESS);
        }
        
        return new CommandResult(MESSAGE_FAILURE);
    }

    /**
     * Return true if able revert to previous activity manager, otherwise return false.
     */
	public boolean undoActivityManger() {
		assert model != null;
		
		if (model.checkStatesInUndoStack()) {
			return false;
		}
		
		model.addStateToRedoStack(new ActivityManager(model.getActivityManager()));
		model.resetData(model.retrievePreviousStateFromUndoStack());
		
		return true;
	}
	
    /**
     * Return true if able revert to previous storage path, otherwise return false.
     */
	public boolean undoStoragePath() {
		assert model != null;
		
		if (model.checkStoragePathInUndoStack()) {
			return false;
		}
		
		// Initialising Config file
        Config initializedConfig;
    	try {
            Optional<Config> configOptional = ConfigUtil.readConfig(Config.DEFAULT_CONFIG_FILE);
            initializedConfig = configOptional.orElse(Config.getInstance());
        } catch (DataConversionException e) {
            initializedConfig = Config.getInstance();
        }
    	
		model.addStoragePathToRedoStack(initializedConfig.getActivityManagerFilePath());
		
		String storagePathToUndo = model.retrievePreviouStoragePathFromUndoStack();
    	
		// Deleting old files
		File oldStorage =  new File(initializedConfig.getActivityManagerFilePath());
		oldStorage.delete();
		
		// Saving configuration
		initializedConfig.setActivityManagerFilePath(storagePathToUndo);
		try {
			ConfigUtil.saveConfig(initializedConfig, initializedConfig.DEFAULT_CONFIG_FILE);
		} catch (IOException e) {
			e.printStackTrace();
		}

    	// Setting up new storage location
		XmlActivityManagerStorage newStorage = new XmlActivityManagerStorage(storagePathToUndo);
		EventsCenter.getInstance().post(new StoragePathChangedEvent(newStorage, model.getActivityManager()));		
		
		// Update status bar
		EventsCenter.getInstance().post(new ModifyStorageEvent(storagePathToUndo));
		
		return true;
	}
}