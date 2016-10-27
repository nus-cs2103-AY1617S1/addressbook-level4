package seedu.taskmanager.logic.commands;

import java.io.IOException;
import java.util.logging.Logger;

import seedu.taskmanager.commons.core.Config;
import seedu.taskmanager.commons.core.LogsCenter;
import seedu.taskmanager.commons.exceptions.DataConversionException;
import seedu.taskmanager.commons.util.ConfigUtil;
import seedu.taskmanager.model.ReadOnlyTaskManager;
import seedu.taskmanager.model.TaskManager;
import seedu.taskmanager.storage.StorageManager;

//@@author A0143641M
/**
 * Saves the program data file at the specified location in a .xml file.
 */
public class SaveCommand extends Command {
    public static final String COMMAND_WORD = "save";
    
    public static final String MESSAGE_ERROR_CONVERTING_FILE = "Error reading from config file: " + Config.DEFAULT_CONFIG_FILE;
    public static final String MESSAGE_SUCCESS = "File path changed! Custom file path specified: %1$s";
    public static final String MESSAGE_ERROR_SAVING_FILE = "Error occured saving to file.";
    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Saves task manager information to the specified storage file path.\n"
            + "Parameters: " + COMMAND_WORD + " FILEPATH.xml \n"
            + "Example: " + COMMAND_WORD +  " " + " data/newtaskbook.xml" + "\n"
            + "Note: file path is limited to .xml format";
    
    private static final Logger logger = LogsCenter.getLogger(SaveCommand.class);

    private String newTaskManagerFilePath;

    public SaveCommand(String newTaskManagerFilePath) {
        this.newTaskManagerFilePath = newTaskManagerFilePath;
        logger.info("New task file path specified: " + newTaskManagerFilePath);
    }
    
    @Override
    public CommandResult execute() {
        String defaultConfigFilePath = Config.DEFAULT_CONFIG_FILE;
        
        try {
            Config currentConfig = ConfigUtil.readConfig(defaultConfigFilePath).orElse(new Config());

            String previousFilePath = currentConfig.getTaskManagerFilePath();
            
            currentConfig.setTaskManagerFilePath(newTaskManagerFilePath);
            ConfigUtil.saveConfig(currentConfig, defaultConfigFilePath);
            
            StorageManager previousStorage = new StorageManager(previousFilePath, currentConfig.getUserPrefsFilePath());
            StorageManager newStorage = new StorageManager(newTaskManagerFilePath, currentConfig.getUserPrefsFilePath());
            
            ReadOnlyTaskManager previousTaskManager = previousStorage.readTaskManager().orElse(new TaskManager());
            newStorage.saveTaskManager(previousTaskManager);
            
            logger.fine("Saved to specified file path: " + newTaskManagerFilePath);
            
            return new CommandResult(String.format(MESSAGE_SUCCESS, newStorage.getTaskManagerFilePath()));
            
        } catch (DataConversionException e) {
            return new CommandResult(MESSAGE_ERROR_CONVERTING_FILE);
        } catch (IOException e) {
            return new CommandResult(MESSAGE_ERROR_SAVING_FILE);
        }
    }
}
