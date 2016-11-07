package seedu.task.logic.commands;

//@@author A0125534L

import java.io.IOException;
import java.util.logging.Logger;

import seedu.task.commons.core.Config;
import seedu.task.commons.core.EventsCenter;
import seedu.task.commons.core.LogsCenter;
import seedu.task.commons.events.storage.StorageLocationChangedEvent;
import seedu.task.model.ReadOnlyTaskBook;
import seedu.task.storage.JsonConfigStorage;
import seedu.task.storage.Storage;

/** Save command that saves current data file to new filepath.
 * 
 * */

public class SaveCommand extends Command {
    
    private Logger logger = LogsCenter.getLogger(SaveCommand.class.getName());
    
    public static final String COMMAND_WORD = "save";

    public static final String MESSAGE_USAGE = COMMAND_WORD + "\n" 
    		+ "Saves data file to new location specified. "
            + "New folders with the file can be auto-created as long as given directory is valid.\n"
    		+ "Main Directory will the dafault save location for any valid but unspecifed file path\n"
            + "Example: " + COMMAND_WORD + " C: /Users/Computin/Desktop/CS2103" + "Take note: No spacing after :\n"
            + "Parameters: FILEPATH (must be valid)\n"
            + "Example: " + COMMAND_WORD + " C:/Users/Computing/Desktop/CS2103";
    
    private static final String MESSAGE_SUCCESS = "Data successfully saved to new location.";
    private static final String MESSAGE_INVALID_PATH = "Filepath given is invalid. Filepath will be reset to old path." + "\n\n" + MESSAGE_USAGE;
    
    private static Config config;
    private String newStorageFilePath, oldStorageFilePath;
    private ReadOnlyTaskBook taskBookManager;
    private static JsonConfigStorage jsonConfigStorage;
    private static Storage storage;
    
    public SaveCommand(String newStorageFilePath) {
        this.oldStorageFilePath = config.getTaskBookFilePath();
        logger.info("Old file path: " + oldStorageFilePath);
        
        this.newStorageFilePath = newStorageFilePath.trim().replace("\\", "/") + "/dowat.xml";
        logger.info("New file path: " + this.newStorageFilePath);
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

        taskBookManager = model.getTaskBook();
        
        config.setTaskBookFilePath(newStorageFilePath);
        indicateStorageLocationChanged();
        try {
            storage.saveTaskBook(taskBookManager, newStorageFilePath);
        } catch (IOException e) {
            handleInvalidFilePathException();
            return new CommandResult(MESSAGE_INVALID_PATH);
        }
        
        saveToConfigJson();
        model.updateFilteredTaskListToShowAll();
        model.updateFilteredEventListToShowAll();
        return new CommandResult(MESSAGE_SUCCESS);
    }

    private void indicateStorageLocationChanged() {
        assert config != null;
        EventsCenter.getInstance().post(new StorageLocationChangedEvent(config));
    }
    
    private void handleInvalidFilePathException() { 
        logger.info("Error writing to filepath. Handling data save exception.");
        assert config != null;
        
        config.setTaskBookFilePath(oldStorageFilePath);  //set back to old filepath
        indicateStorageLocationChanged(); 
        indicateAttemptToExecuteIncorrectCommand();
        
        try {
            storage.saveTaskBook(taskBookManager, newStorageFilePath);
        } catch (IOException e) {
            logger.severe("Error saving task manager");
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

}