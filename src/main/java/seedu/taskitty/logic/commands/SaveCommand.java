package seedu.taskitty.logic.commands;

import java.io.IOException;
import java.util.Set;

import seedu.taskitty.commons.exceptions.IllegalValueException;
import seedu.taskitty.commons.util.ConfigUtil;
import seedu.taskitty.commons.util.StringUtil;
import seedu.taskitty.model.task.Task;
import seedu.taskitty.storage.StorageManager;
import seedu.taskitty.commons.core.Config;

public class SaveCommand extends Command{
    
    public static final String COMMAND_WORD = "save";

    public static final String MESSAGE_PARAMETER = COMMAND_WORD
            + " filepath";
    public static final String MESSAGE_USAGE = "This command saves data in TasKitty to a location of your choice, Meow!";

    public static final String MESSAGE_SUCCESS = "Data saved to: %1$s";
    public static final String MESSAGE_FAILED = "Failed to save data to: %1$s";
    
    public final String filepath;
    
    public SaveCommand(String filepath) {
        this.filepath = filepath;
    }

    @Override
    public CommandResult execute() {
        Config config = new Config();
        String configFile = Config.DEFAULT_CONFIG_FILE;
        
        try {
            config.setTaskManagerFilePath(filepath + config.getTaskManagerFilePath());
            ConfigUtil.saveConfig(config, configFile);
            
            new StorageManager(config.getTaskManagerFilePath(), config.getUserPrefsFilePath());
            
            return new CommandResult(MESSAGE_SUCCESS);
        } catch (IOException io) {
            return new CommandResult(MESSAGE_FAILED + StringUtil.getDetails(io));
        }
    }

    @Override
    public void saveStateIfNeeded(String commandText) {
    }

}
