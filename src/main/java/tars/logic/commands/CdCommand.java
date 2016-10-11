package tars.logic.commands;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

import tars.commons.core.Config;
import tars.commons.util.ConfigUtil;
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

    public static final String MESSAGE_SUCCESS = "Directory of tars.xml changed to: '%1$s'. "
            + "Please restart TARS for changes to take effect.";

    public static final String MESSAGE_FAILURE = "Unable to write to location, please choose another directory";

    private final String filePath;
    private final static String configFilePath = "config.json";
    private final static String xmlFileExt = "xml";

    public CdCommand(String filepath) {
        this.filePath = filepath;
    }

    public final static String getXmlFileExt() {
        return xmlFileExt;
    }

    @Override
    public String toString() {
        return this.filePath;
    }

    @Override
    public CommandResult execute() {
        Config config = new Config();
        config.setTarsFilePath(filePath);
        XmlTarsStorage xmlTarsStorage = new XmlTarsStorage(filePath);

        try {
            xmlTarsStorage.saveTars(model.getTars(), filePath); // copy data
                                                                // from previous
                                                                // file to new
                                                                // file
            Path path = Paths.get(filePath);
            if (!Files.exists(path)) {
                return new CommandResult(MESSAGE_FAILURE);
            }
            ConfigUtil.saveConfig(config, configFilePath); // update
                                                           // tarsFilePath in
                                                           // config.json
            return new CommandResult(String.format(MESSAGE_SUCCESS, filePath));
        } catch (IOException ioe) {
            return new CommandResult(MESSAGE_FAILURE);
        }
    }

}
