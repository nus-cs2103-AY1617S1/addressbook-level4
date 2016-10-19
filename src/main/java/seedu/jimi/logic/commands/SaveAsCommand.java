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

    private static final String DEFAULT_FILE_PATH = (new Config()).getTaskBookFilePath();
    
    public static final String MESSAGE_USAGE = 
            COMMAND_WORD + ": Set a new save directory for all your tasks and events in Jimi.\n"
            + "Parameters: FILEPATH/FILENAME.xml or FILEPATH\\FILENAME.xml \n"
            + "Jimi rejects these characters in file paths: * & % ? : \" < > \n"
            + "and these characters in file names: / : * \" < > ? | \\ \n"
            + "Example: " + COMMAND_WORD + " C:/dropbox/taskbook.xml";

    public static final String MESSAGE_SUCCESS = "Save directory changed: %1$s";
    public static final String MESSAGE_CONFIG_FILE_NOT_FOUND = "Config file not found. ";
    public static final String MESSAGE_DUPLICATE_SAVE_DIRECTORY = "New save directory is the same as the old save directory.";
    public static final String MESSAGE_ERROR_SAVING = 
            "Error: %1$s \n"
            + "Reverting back to default save file path: " + DEFAULT_FILE_PATH;
    
    private static String configFilePath = Config.DEFAULT_CONFIG_FILE;
    
    private String taskBookFilePath;
    
    /**
     * Empty constructor for stub usage
     */
    public SaveAsCommand() {}
    
    /**
     * Convenience constructor using raw values.
     */
    public SaveAsCommand(String filePath)  {
        this.taskBookFilePath = filePath;
    }
    
    public static void setConfigFilePath(String newConfigFilePath) {
        configFilePath = newConfigFilePath;
    }
    
    @Override
    public CommandResult execute() {
        try {
            Config config = ConfigUtil.readConfig(configFilePath).orElse(new Config());
            
            String oldTaskBookFilePath = config.getTaskBookFilePath();
            if (oldTaskBookFilePath.equals(taskBookFilePath)) {
                indicateAttemptToExecuteIncorrectCommand();
                return new CommandResult(String.format(MESSAGE_DUPLICATE_SAVE_DIRECTORY));
            }
            config.setTaskBookFilePath(taskBookFilePath);
            
            ConfigUtil.saveConfig(config, configFilePath);
            
            StorageManager oldStorage = new StorageManager(oldTaskBookFilePath, config.getUserPrefsFilePath());
            StorageManager newStorage = new StorageManager(taskBookFilePath, config.getUserPrefsFilePath());
            
            ReadOnlyTaskBook oldTaskBook = oldStorage.readTaskBook(oldTaskBookFilePath).orElse(new TaskBook());
            
            newStorage.saveTaskBook(oldTaskBook);
            
            return new CommandResult(String.format(MESSAGE_SUCCESS, config.getTaskBookFilePath()));
        } catch (DataConversionException e) {
            return new CommandResult(String.format(MESSAGE_CONFIG_FILE_NOT_FOUND));
        } catch (IOException e) {
            saveAsDefaultConfig();
            return new CommandResult(String.format(MESSAGE_ERROR_SAVING, e.getMessage()));
        }
    }

    /**
     * Reverts current config to default config specified in Config Class.
     */
    private static void saveAsDefaultConfig() {
        try {
            ConfigUtil.saveConfig(new Config(), Config.DEFAULT_CONFIG_FILE);
        } catch (IOException e) {
            e.printStackTrace();
        }     
    }

    @Override
    public boolean isValidCommandWord(String commandWord) {
        return commandWord.equals(COMMAND_WORD);
    }
}
