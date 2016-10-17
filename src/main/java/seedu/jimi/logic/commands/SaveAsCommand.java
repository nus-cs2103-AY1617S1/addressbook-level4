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
    public static final String MESSAGE_DUPLICATE_TASK = "This save directory is originally used in Jimi";
    public static final String MESSAGE_CONFIG_FILE_NOT_FOUND = "Config file is not found. ";
    public static final String MESSAGE_UPDATING_SAVE_DIR = "There is an error updating the new save directory.";

    private String taskBookFilePath;

    /**
     * Convenience constructor using raw values.
     */
    public SaveAsCommand(String filePath)  {
        this.taskBookFilePath = filePath.concat(".xml");
    }
    
    @Override
    public CommandResult execute() {
        String defaultConfigFilePathUsed = Config.DEFAULT_CONFIG_FILE;
        try {
            Config config = ConfigUtil.readConfig(defaultConfigFilePathUsed).orElse(new Config());
            
            String oldTaskBookFilePath = config.getTaskBookFilePath();
            config.setTaskBookFilePath(taskBookFilePath);
            
            ConfigUtil.saveConfig(config, defaultConfigFilePathUsed);
            
            StorageManager oldStorage = new StorageManager(oldTaskBookFilePath, config.getUserPrefsFilePath());
            StorageManager newStorage = new StorageManager(taskBookFilePath, config.getUserPrefsFilePath());
            
            ReadOnlyTaskBook oldTaskBook = oldStorage.readTaskBook(oldTaskBookFilePath).orElse(new TaskBook());
            newStorage.saveTaskBook(oldTaskBook);
            
            return new CommandResult(String.format(MESSAGE_SUCCESS, newStorage.getAddressBookFilePath()));
        } catch (DataConversionException e) {
            return new CommandResult(String.format(MESSAGE_CONFIG_FILE_NOT_FOUND));
        } catch (IOException e) {
            return new CommandResult(String.format(MESSAGE_UPDATING_SAVE_DIR));
        }
    }
    

}
