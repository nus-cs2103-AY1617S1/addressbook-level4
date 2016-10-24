package seedu.taskitty.logic.commands;

import static seedu.taskitty.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;

import java.io.IOException;

import seedu.taskitty.commons.util.ConfigUtil;
import seedu.taskitty.commons.util.StringUtil;
import seedu.taskitty.storage.StorageManager;
import seedu.taskitty.commons.core.Config;
import seedu.taskitty.commons.exceptions.IllegalValueException;

//@@author A0135793W
public class SaveCommand extends Command{
    
    public static final String COMMAND_WORD = "save";

    public static final String MESSAGE_PARAMETER = COMMAND_WORD
            + " filepath";
    public static final String MESSAGE_USAGE = "This command saves data in TasKitty to a location of your choice, Meow!\n"
            + "You can specify a filepath, eg save /Users/<username>/Desktop\n"
            + "Or you can simply specify a filename, eg save FileData, in which case a folder called FileData will be created in "
            + "the same location as the application TasKitty, Meow!";

    public static final String MESSAGE_SUCCESS = "Data saved to: %1$s";
    public static final String MESSAGE_FAILED = "Failed to save data to: %1$s";
    
    public final String filepath;
    
    public SaveCommand(String filepath) throws IllegalValueException{
        if (filepath.toCharArray().length == 0) {
            throw new IllegalValueException(String.format(MESSAGE_INVALID_COMMAND_FORMAT, MESSAGE_USAGE));
        };
        this.filepath = filepath;
    }

    @Override
    public CommandResult execute() {
        Config config = new Config();
        String configFile = Config.DEFAULT_CONFIG_FILE;
        
        try {
            config.setTaskManagerFilePath(filepath + "/" + config.getTaskManagerFilePath());
            ConfigUtil.saveConfig(config, configFile);
            
            new StorageManager(config.getTaskManagerFilePath(), config.getUserPrefsFilePath());
            
            return new CommandResult(String.format(MESSAGE_SUCCESS, filepath));
        } catch (IOException io) {
            return new CommandResult(MESSAGE_FAILED + StringUtil.getDetails(io));
        }
    }

    @Override
    public void saveStateIfNeeded(String commandText) {
    }

}
