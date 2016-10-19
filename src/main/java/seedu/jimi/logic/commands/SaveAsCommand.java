package seedu.jimi.logic.commands;

import java.io.IOException;
import seedu.jimi.commons.core.Config;
import seedu.jimi.commons.exceptions.DataConversionException;
import seedu.jimi.commons.util.ConfigUtil;
import seedu.jimi.model.ReadOnlyTaskBook;
import seedu.jimi.model.TaskBook;
import seedu.jimi.storage.StorageManager;

/**
 *  
 * @author Wei Yin 
 * 
 * Sets save directory for the tasks and events in Jimi.
 */
public class SaveAsCommand extends Command {

    public static final String COMMAND_WORD = "saveas";

    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Set a new save directory for all your tasks and events in Jimi.\n"
            + "Parameters: FILEPATH/FILENAME.xml \n"
            + "Example: " + COMMAND_WORD
            + " C:/dropbox/taskbook.xml";

    public static final String MESSAGE_SUCCESS = "Save directory changed: %1$s";
    public static final String MESSAGE_CONFIG_FILE_NOT_FOUND = "Config file is not found. ";
    public static final String MESSAGE_UPDATING_SAVE_DIR = "There is an error updating the new save directory.";
    public static final String MESSAGE_DUPLICATE_SAVE_DIRECTORY = "New save directory is the same as the old save directory.";

    private String taskBookFilePath;
    
    private static String configFilePathUsed = Config.DEFAULT_CONFIG_FILE;

    /**
     * Convenience constructor using raw values.
     */
    public SaveAsCommand(String filePath)  {
        this.taskBookFilePath = filePath.concat(".xml");
    }
    
    public static void setConfigFilePath(String newConfigFilePathUsed) {
        configFilePathUsed = newConfigFilePathUsed;
    }
    
    @Override
    public CommandResult execute() {
        try {
            Config config = ConfigUtil.readConfig(configFilePathUsed).orElse(new Config());
            
            String oldTaskBookFilePath = config.getTaskBookFilePath();
            if (oldTaskBookFilePath.equals(taskBookFilePath))   {
                indicateAttemptToExecuteIncorrectCommand();
                return new CommandResult(String.format(MESSAGE_DUPLICATE_SAVE_DIRECTORY));
            }
            config.setTaskBookFilePath(taskBookFilePath);
            
            ConfigUtil.saveConfig(config, configFilePathUsed);
            
            StorageManager oldStorage = new StorageManager(oldTaskBookFilePath, config.getUserPrefsFilePath());
            StorageManager newStorage = new StorageManager(taskBookFilePath, config.getUserPrefsFilePath());
            
            ReadOnlyTaskBook oldTaskBook = oldStorage.readTaskBook(oldTaskBookFilePath).orElse(new TaskBook());
            newStorage.saveTaskBook(oldTaskBook);
            
            return new CommandResult(String.format(MESSAGE_SUCCESS, config.getTaskBookFilePath()));
        } catch (DataConversionException e) {
            return new CommandResult(String.format(MESSAGE_CONFIG_FILE_NOT_FOUND));
        } catch (IOException e) {
            return new CommandResult(String.format(MESSAGE_UPDATING_SAVE_DIR));
        }
    }

    @Override
    public boolean isValidCommandWord(String commandWord) {
        return commandWord.equals(COMMAND_WORD);
    }
    
    
}
