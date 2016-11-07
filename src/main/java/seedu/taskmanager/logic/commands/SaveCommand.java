package seedu.taskmanager.logic.commands;

import java.io.IOException;
import java.util.logging.Logger;

import seedu.taskmanager.commons.core.Config;
import seedu.taskmanager.commons.core.LogsCenter;
import seedu.taskmanager.commons.exceptions.DataConversionException;
import seedu.taskmanager.commons.exceptions.IllegalValueException;
import seedu.taskmanager.commons.util.ConfigUtil;
import seedu.taskmanager.commons.util.FileUtil;
import seedu.taskmanager.model.ReadOnlyTaskManager;
import seedu.taskmanager.model.TaskManager;
import seedu.taskmanager.storage.StorageManager;

//@@author A0143641M
/**
 * Saves the program data file at the specified location in a .xml file.
 */
public class SaveCommand extends Command {
    public static final String COMMAND_WORD = "save";
    
    public static final String DEFAULT_FILE_PATH = "taskmanager.xml";
    public static final String MESSAGE_CONFIG_FILE_NOT_FOUND = "Config file not found.";
    public static final String MESSAGE_SUCCESS = "File path changed! Custom file path specified: %1$s";
    public static final String MESSAGE_ERROR_SAVING_FILE = "Error occured saving to file.";
    public static final String MESSAGE_INVALID_FILE_PATH = "Specified directory is invalid!";
    public static final String MESSAGE_DUPLICATE_SAVE_DIRECTORY = "New directory is the same as the old directory"; 
    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Saves task manager information to the specified storage file path.\n"
            + "Parameters: " + COMMAND_WORD + " FILEPATH.xml \n"
            + "Example: " + COMMAND_WORD +  " " + " data/newtaskbook.xml" + "\n"
            + "Note: file path is limited to .xml format" + "\n"
            + "Note: this command cannot be undone";
    
    private static final Logger logger = LogsCenter.getLogger(SaveCommand.class);

    private static String configFilePath = Config.DEFAULT_CONFIG_FILE;
    private String taskManagerFilePath;

    public SaveCommand() {
        this.taskManagerFilePath = null; 
    }
    
    public SaveCommand(String newTaskManagerFilePath) throws IllegalValueException {
        if (!FileUtil.isValidFilePath(newTaskManagerFilePath)) {
            throw new IllegalValueException(MESSAGE_INVALID_FILE_PATH);
        }
        
        this.taskManagerFilePath = newTaskManagerFilePath;
        
        logger.info("New task file path specified: " + newTaskManagerFilePath);
    }
    
    @Override
    public CommandResult execute() {
        assert FileUtil.isValidFilePath(taskManagerFilePath);
        
        try {
            Config configFile = ConfigUtil.readConfig(configFilePath).orElse(new Config());

            String previousTaskManagerFilePath = configFile.getTaskManagerFilePath();
            if (previousTaskManagerFilePath.equals(taskManagerFilePath)) {
                indicateAttemptToExecuteIncorrectCommand();
                return new CommandResult(String.format(MESSAGE_DUPLICATE_SAVE_DIRECTORY));
            }
            
            configFile.setTaskManagerFilePath(taskManagerFilePath);
            
            ConfigUtil.saveConfig(configFile, configFilePath);
            
            indicateStoragePathChanged(previousTaskManagerFilePath, taskManagerFilePath);
            
            logger.fine("New data file created. Saved to specified file path: " + taskManagerFilePath);

 //           model.saveAction(previousTaskManagerFilePath, taskManagerFilePath);
            return new CommandResult(String.format(MESSAGE_SUCCESS, configFile.getTaskManagerFilePath()));
            
        } catch (DataConversionException e) {
            return new CommandResult(MESSAGE_CONFIG_FILE_NOT_FOUND);
        } catch (IOException e) {
            return new CommandResult(MESSAGE_INVALID_FILE_PATH);
        }
    }
}
