package seedu.address.logic.commands;

import java.io.IOException;

import java.util.logging.Logger;

import seedu.address.commons.core.Config;
import seedu.address.commons.core.LogsCenter;
import seedu.address.commons.util.ConfigUtil;

//@@author A0147890U
public class SpecifyStorageCommand extends Command {
    private static final Logger logger = LogsCenter.getLogger(SpecifyStorageCommand .class);
    
    public static final String COMMAND_WORD = "storage";

    public static final String MESSAGE_USAGE = COMMAND_WORD
            + ": Sets your storage folder for your data files in Simply." + "Parameters: storage folder path";

    public static final String SPECIFY_STORAGE_SUCCESS = "storage folder changed to %1$s";

    private final String folderPath;

    public SpecifyStorageCommand(String folderPath) {
        this.folderPath = folderPath;
    }
    
    public String getTargetFilePath() {
        return this.folderPath;
    }
    
    @Override
    public CommandResult execute() {
        
        model.addToUndoStack();
        
        try {
            Config config = model.getConfig();
            config.setAddressBookFilePath(folderPath);
            ConfigUtil.saveConfig(config, Config.DEFAULT_CONFIG_FILE);
        } catch (IOException e) {
            logger.warning("config file could not be saved to");
        }
        
        return new CommandResult(String.format(SPECIFY_STORAGE_SUCCESS, this.folderPath));

    }

}