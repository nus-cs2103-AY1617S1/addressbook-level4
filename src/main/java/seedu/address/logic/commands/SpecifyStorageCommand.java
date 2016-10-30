package seedu.address.logic.commands;

import java.io.File;
import java.io.IOException;

import com.fasterxml.jackson.core.JsonEncoding;
import com.fasterxml.jackson.core.JsonFactory;
import com.fasterxml.jackson.core.JsonGenerationException;
import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.JsonMappingException;

import seedu.address.commons.core.Config;
import seedu.address.commons.util.ConfigUtil;

//@@author A0147890U
public class SpecifyStorageCommand extends Command {

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
            System.out.println("oops");
        }
        
        return new CommandResult(String.format(SPECIFY_STORAGE_SUCCESS, this.folderPath));

    }

}