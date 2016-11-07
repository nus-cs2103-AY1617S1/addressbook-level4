package seedu.task.storage;

import java.io.File;
import java.io.IOException;
import java.util.Optional;
import java.util.logging.Logger;

import seedu.task.commons.core.Config;
import seedu.task.commons.core.LogsCenter;
import seedu.task.commons.exceptions.DataConversionException;
import seedu.task.commons.util.FileUtil;

//@@author A0125534L

/**
 * A class to access Config stored in the hard disk as a json file
 * 
 */


public class JsonConfigStorage implements ConfigStorage {

    private static final Logger logger = LogsCenter.getLogger(JsonConfigStorage.class);
    
    private String filePath;
    
    public JsonConfigStorage(String filePath) {
        this.filePath = filePath;
    }
    // read from project main directory 
    @Override
    public Optional<Config> readConfigFile() throws DataConversionException, IOException {
        return readConfig(filePath);
    }
    // save to project main directory
    @Override
    public void saveConfigFile(Config config) throws IOException {
        saveConfig(config, filePath);
    }
    
    /**
     * Similar to {@link #readConfigFile()}
     * @param configFilePath location of the data. Cannot be null.
     * @throws DataConversionException if the file format is not as expected.
     */
    
    //read from specified saved file path
    public Optional<Config> readConfig(String configFilePath) throws DataConversionException {
        assert configFilePath != null;

        File configFile = new File(configFilePath);

        if (!configFile.exists()) {
            logger.info("Config file "  + configFile + " not found");
            return Optional.empty();
        }

        Config config;

        try {
            config = FileUtil.deserializeObjectFromJsonFile(configFile, Config.class);
        } catch (IOException e) {
            logger.warning("Error reading from config file " + configFile + ": " + e);
            throw new DataConversionException(e);
        }

        return Optional.of(config);
    }

    /**
     * Similar to {@link #saveConfigFile(Config)}
     * @param configFilePath location of the data. Cannot be null.
     */
    // save to specified file path
    private void saveConfig(Config config, String configFilePath) throws IOException {
        assert config != null;
        assert configFilePath != null;
        assert !configFilePath.isEmpty();

        FileUtil.serializeObjectToJsonFile(new File(configFilePath), config);
    }

}