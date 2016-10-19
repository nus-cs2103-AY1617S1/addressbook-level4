package seedu.taskmanager.logic.commands;

import java.io.IOException;
import java.util.Optional;
import java.util.logging.Logger;

import seedu.taskmanager.commons.core.Config;
import seedu.taskmanager.commons.core.LogsCenter;
import seedu.taskmanager.commons.exceptions.DataConversionException;
import seedu.taskmanager.commons.util.ConfigUtil;
import seedu.taskmanager.commons.util.StringUtil;
import seedu.taskmanager.model.ReadOnlyTaskManager;
import seedu.taskmanager.model.TaskManager;
import seedu.taskmanager.model.item.ItemType;
import seedu.taskmanager.storage.StorageManager;

/**
 * Saves the program data file at the specified location
 */
public class SaveAsCommand extends Command {
    public static final String COMMAND_WORD = "saveAs";
    
    public static final String MESSAGE_ERROR_CONVERTING_FILE = "Error reading from config file: " + Config.DEFAULT_CONFIG_FILE;
    public static final String MESSAGE_SUCCESS = "File path changed! Custom file path specified: %1$s";
    public static final String MESSAGE_SAME_FILE_PATH = "File path is already saved at the specified location!";
    public static final String MESSAGE_ERROR_SAVING_FILE = "Error occured saving to file.";
    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Saves task manager information to the specified storage file path. \n"
            + "Parameters: " + COMMAND_WORD + " FILEPATH \n"
            + "Example: " + COMMAND_WORD +  " " + " data/newtaskbook.xml" + "\n"
            + "Note: file path is limited to .xml format";
//            + "Note: " + COMMAND_WORD + " can be replaced by " + SHORT_COMMAND_WORD + "\n"
    
    private static final Logger logger = LogsCenter.getLogger(SaveAsCommand.class);

    private String newTaskManagerFilePath;
    
    // CONSTRUCTOR
    public SaveAsCommand(String newTaskManagerFilePath) {
        this.newTaskManagerFilePath = newTaskManagerFilePath;
    }
    
    @Override
    public CommandResult execute() {
        String defaultConfigFilePath = Config.DEFAULT_CONFIG_FILE;
        
        try {
            Config currentConfig = ConfigUtil.readConfig(defaultConfigFilePath).orElse(new Config());

            String previousFilePath = currentConfig.getTaskManagerFilePath();
            
            currentConfig.setTaskManagerFilePath(newTaskManagerFilePath);
            ConfigUtil.saveConfig(currentConfig, newTaskManagerFilePath);
            
            StorageManager previousStorage = new StorageManager(previousFilePath, currentConfig.getUserPrefsFilePath());
            StorageManager newStorage = new StorageManager(newTaskManagerFilePath, currentConfig.getUserPrefsFilePath());
            
            ReadOnlyTaskManager previousTaskManager = previousStorage.readTaskManager().orElse(new TaskManager());
            newStorage.saveTaskManager(previousTaskManager);
            

            logger.info(currentConfig.toString());
            logger.info(String.format("PRINT THIS", newTaskManagerFilePath));

            
            return new CommandResult(String.format(MESSAGE_SUCCESS, newStorage.getTaskManagerFilePath()));
            
        } catch (DataConversionException e) {
            return new CommandResult(MESSAGE_ERROR_CONVERTING_FILE);
        } catch (IOException e) {
            return new CommandResult(MESSAGE_ERROR_SAVING_FILE);
        }
    }
    
// need to use Config class to access config.json file and edit accordingly
// test to check where the file is going to be saved by using Logger.info(<configobject>.toString());
// need to edit Storage.saveUserPrefs()
// probably need to use storage manager
// Model raises a TaskManagerChangedEvent when the Address Book data are changed, instead of asking
// *  Storage to save the updates to the hard disk.
// *  EventsCenter calls handleTaskManagerChangedEvent() to save the file using Storage class
// Model stores the TaskManager data
// * 
// Storage component can save UserPref objects in json format and read it back
// * can save TaskManager data in xml format and read it back
// Certain properties of the application can be controlled (e.g App name, logging level)
// * through the configuration file (default: config.json)
}
