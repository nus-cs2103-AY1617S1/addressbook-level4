package seedu.task.logic.commands;

import java.io.IOException;

import seedu.task.commons.core.Config;
import seedu.task.commons.util.*;

public class SetStorageCommand extends Command {
    
          
    public static final String COMMAND_WORD = "setstorage";
    public static final String MESSAGE_SUCCESS = "Changed storage location to %1$s";
    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Changes the storage location for the task manager. "
            + "Parameters: FILEPATH"
            + "Example: " + COMMAND_WORD
            + " C:/Users/Jim/Documents/data/taskmanager.xml";
    
    private String filePath;
    
    public SetStorageCommand(String filePath) {
        this.filePath = filePath;
    }

    public CommandResult execute() {
        Config newConfig;
        newConfig = new Config();
        newConfig.setTaskManagerFilePath(this.filePath);
        try {
            ConfigUtil.saveConfig(newConfig, Config.DEFAULT_CONFIG_FILE);
            
            return new CommandResult(String.format(MESSAGE_SUCCESS, filePath));
        } catch (IOException e) {
            return new CommandResult(String.format("Failed to save config file : " + StringUtil.getDetails(e)));
        }
    }
}