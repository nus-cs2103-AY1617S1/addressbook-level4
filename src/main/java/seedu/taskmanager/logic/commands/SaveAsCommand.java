package seedu.taskmanager.logic.commands;

import java.io.IOException;
import java.util.Optional;
import java.util.logging.Logger;

import seedu.taskmanager.commons.core.Config;
import seedu.taskmanager.commons.core.LogsCenter;
import seedu.taskmanager.commons.exceptions.DataConversionException;
import seedu.taskmanager.commons.util.ConfigUtil;
import seedu.taskmanager.commons.util.StringUtil;
import seedu.taskmanager.model.item.ItemType;

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
    
    public final String defaultConfigFilePath = Config.DEFAULT_CONFIG_FILE;

    private String newConfigFilePath;
    
    // CONSTRUCTOR
    public SaveAsCommand(String newConfigFilePath) {
        this.newConfigFilePath = newConfigFilePath;
    }
    
    @Override
    public CommandResult execute() {
    
        try {
            Config currentConfig = ConfigUtil.readConfig(defaultConfigFilePath).orElse(new Config());

            if(newConfigFilePath.equals(currentConfig.getTaskManagerFilePath())) {
                return new CommandResult(MESSAGE_SAME_FILE_PATH);
            }

            
            currentConfig.setTaskManagerFilePath(newConfigFilePath);
            ConfigUtil.saveConfig(currentConfig, newConfigFilePath);
            logger.info(currentConfig.toString());
            return new CommandResult(String.format(MESSAGE_SUCCESS, newConfigFilePath));
/*            logger.info("Using config file : " + configFilePathUsed);

            //Update config file in case it was missing to begin with or there are new/unused fields
            try {
                ConfigUtil.saveConfig(initializedConfig, configFilePathUsed);
            } catch (IOException e) {
                logger.warning("Failed to save config file : " + StringUtil.getDetails(e));
            }
            return initializedConfig;

            return new CommandResult(MESSAGE_SUCCESS);
            
*/        
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
