package seedu.address.logic.commands;

import java.io.IOException;

import seedu.address.commons.core.Config;
import seedu.address.commons.util.ConfigUtil;
import seedu.address.commons.util.StringUtil;
import seedu.address.storage.StorageManager;
import seedu.address.storage.XmlAddressBookStorage;
import seedu.address.storage.XmlFileStorage;

public class SetSavePathCommand extends Command {

    public static final String COMMAND_WORD = "setsavepath";
    
    private String savedPathLink;
    
    public SetSavePathCommand(String arguments) {
        this.savedPathLink = arguments;
    }

    @Override
    public CommandResult execute() {
        
        Config config = new Config();
        String configFilePathUsed = Config.DEFAULT_CONFIG_FILE;
        
        try {
            config.setAddressBookFilePath(savedPathLink);
            ConfigUtil.saveConfig(config, configFilePathUsed);
            new CommandResult("Saved Path Changed: " + savedPathLink);
            
            new StorageManager(config.getAddressBookFilePath(), config.getUserPrefsFilePath());
            new XmlAddressBookStorage(savedPathLink);
            
            return new CommandResult("Updated");
        } catch (IOException e) {
            System.out.println("Failed to save config file : " + StringUtil.getDetails(e));
            return new CommandResult("Yo mama");
        }
        
        
    }

}
