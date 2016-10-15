package seedu.address.logic.commands;

import java.io.IOException;

import seedu.address.commons.core.Config;
import seedu.address.commons.util.ConfigUtil;
import seedu.address.commons.util.StringUtil;
import seedu.address.storage.StorageManager;
import seedu.address.storage.XmlFileStorage;

public class SetPathCommand extends Command {

    public static final String COMMAND_WORD = "setpath";
    
    private String savedPathLink;
    
    public SetPathCommand(String arguments) {
        this.savedPathLink = arguments;
    }

    @Override
    public CommandResult execute() {
        
        Config config = new Config();
        String configFilePathUsed = Config.DEFAULT_CONFIG_FILE;
        
        try {
            config.setTaskSchedulerFilePath(savedPathLink);
            ConfigUtil.saveConfig(config, configFilePathUsed);
            return new CommandResult("Saved Path Changed: " + savedPathLink + "\n Updated \n");
        } catch (IOException e) {
            return new CommandResult("Failed to save config file : " + StringUtil.getDetails(e));
        }
        
        
    }

}
