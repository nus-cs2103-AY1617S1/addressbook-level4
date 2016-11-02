//@@author A0138431L
package seedu.savvytasker.logic.commands;

import java.io.IOException;
import java.util.logging.Logger;

import seedu.savvytasker.commons.core.Config;
import seedu.savvytasker.commons.core.EventsCenter;
import seedu.savvytasker.commons.core.LogsCenter;
import seedu.savvytasker.commons.events.storage.StorageLocationChangedEvent;
import seedu.savvytasker.model.ReadOnlySavvyTasker;
import seedu.savvytasker.storage.JsonConfigStorage;
import seedu.savvytasker.storage.Storage;

/**
 * Save all tasks in the user inputed filepath
 */
public class SaveCommand extends ModelRequiringCommand {

	private Logger logger = LogsCenter.getLogger(SaveCommand.class.getName());

	public static final String COMMAND_WORD = "save";

    public static final String MESSAGE_USAGE = COMMAND_WORD
            + ": Saves all tasks in a json file.\n"
            + "Parameters: FILE_PATH [n/FILE_NAME]\n"
            + "Example: " + COMMAND_WORD + " /Users/ruiwen905/Desktop n/Jim Task";

    private static final String MESSAGE_SUCCESS = "Data successfully saved from old location: %1$s to new location: %2$s";
    private static final String MESSAGE_INVALID_PATH = "Given filepath: %1$s is invalid.";
    
    private static Config config;
    private static Storage storage;
    //private String newFileName, oldFileName;
    private String newStorageFilePath;
    private String oldStorageFilePath;
    private ReadOnlySavvyTasker savvyTasker;
    private static JsonConfigStorage jsonConfigStorage;
    
    public SaveCommand(String newStorageFilePath) {
    	
    	this.oldStorageFilePath = config.getSavvyTaskerFilePath();
        logger.info("Old file path: " + oldStorageFilePath);
        
        this.newStorageFilePath = newStorageFilePath + "/savvytasker.xml";
        logger.info("New file path: " + newStorageFilePath);
        jsonConfigStorage = new JsonConfigStorage(Config.DEFAULT_CONFIG_FILE);
    }
    
    public static void setConfig(Config c) {
        config = c;
    }
    
    public static void setStorage(Storage s) {
        storage = s;
    }
    
	@Override
	public CommandResult execute() {
		
		assert config != null;
        assert jsonConfigStorage != null;

        savvyTasker = model.getSavvyTasker();
        
        config.setSavvyTaskerFilePath(newStorageFilePath);

        indicateStorageLocationChanged();
        
        try {
        	
            storage.saveSavvyTasker(savvyTasker, newStorageFilePath);
            
        } catch (IOException e) {
        	
            handleInvalidFilePathException();
            return new CommandResult(String.format(MESSAGE_INVALID_PATH, newStorageFilePath));
            
        }
        
        saveToConfigJson();
        model.updateFilteredListToShowActive();
        return new CommandResult(String.format(MESSAGE_SUCCESS, oldStorageFilePath, newStorageFilePath));		
	}
	
	private void indicateStorageLocationChanged() {
        assert config != null;
        EventsCenter.getInstance().post(new StorageLocationChangedEvent(config));
    }
    
    private void handleInvalidFilePathException() {
    	
        logger.info("Error writing to filepath. Handling data save exception.");
        assert config != null;
        
        config.setSavvyTaskerFilePath(oldStorageFilePath);  
        indicateStorageLocationChanged();
        
        try {
        	
            storage.saveSavvyTasker(savvyTasker, newStorageFilePath);
            
        } catch (IOException e) {
           
        	logger.severe("Error saving savvy tasker");
        	
        }
        
        saveToConfigJson();
    }
    
    private void saveToConfigJson() {
        try {
            jsonConfigStorage.saveConfigFile(config);
        } catch (IOException e) {
            logger.severe("save to config json error");
        }
    }

	@Override
	public boolean canUndo() {
		return false;
	}

	@Override
	public boolean redo() {
		return false;
	}

	@Override
	public boolean undo() {
		return false;
	}

	@Override
	public boolean isUndo() {
		return false;
	}

	@Override
	public boolean isRedo() {
		return false;
	}

}
//@@author 
