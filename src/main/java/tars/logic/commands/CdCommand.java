package tars.logic.commands;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

import tars.commons.core.Config;
import tars.commons.util.ConfigUtil;
import tars.storage.Storage;
import tars.storage.StorageManager;
import tars.storage.XmlTarsStorage;

/**
 * Changes the directory of the Tars storage file, tars.xml
 * 
 * @@author A0124333U
 */

public class CdCommand extends Command {

    public static final String COMMAND_WORD = "cd";

    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Changes the directory of the "
            + "TARS storage file, tars.xml \n" + "Parameters: FILEPATH.xml \n" + "Example: " + COMMAND_WORD
            + " data/tars.xml";

    public static final String MESSAGE_INVALID_FILEPATH = "Invalid file path. File paths should not"
            + " include any white spaces and should end with the file type .xml \n" + "Example: " + COMMAND_WORD
            + " data/tars.xml";

    public static final String MESSAGE_SUCCESS = "Change Directory Success! Directory of TARS storage file"
            + " changed to: '%1$s'.";

    public static final String MESSAGE_FAILURE = "Unable to write to location, please choose another directory";

    private final String newFilePath;
    private final static String xmlFileExt = "xml";;
    private Storage storageUpdater = new StorageManager();

    public CdCommand(String filepath) {
        this.newFilePath = filepath;
    }

    public final static String getXmlFileExt() {
        return xmlFileExt;
    }

    @Override
    public String toString() {
        return this.newFilePath;
    }

    @Override
    public CommandResult execute() {
        Config newConfig = new Config();
        newConfig.setTarsFilePath(newFilePath);
        XmlTarsStorage xmlTarsStorage = new XmlTarsStorage(newFilePath);

        try {
            xmlTarsStorage.saveTars(model.getTars(), newFilePath); // try to save TARS data into new file
            
            if (!isFileSavedSuccessfully(newFilePath)) {
                return new CommandResult(MESSAGE_FAILURE); 
            }
            
            storageUpdater.updateTarsStorageDirectory(newFilePath, newConfig);
            ConfigUtil.updateConfig(newConfig);

            return new CommandResult(String.format(MESSAGE_SUCCESS, newFilePath));
            
        } catch (IOException ioe) {
            return new CommandResult(MESSAGE_FAILURE);
        }
    }

    private boolean isFileSavedSuccessfully(String filePath) {
        Path path = Paths.get(filePath);
        if (!Files.exists(path)) {
            return false;
        }

        return true;
    }

}
