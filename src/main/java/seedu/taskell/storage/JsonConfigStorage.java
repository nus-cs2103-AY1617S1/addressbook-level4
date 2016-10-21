package seedu.taskell.storage;

import java.io.File;
import java.io.IOException;
import java.util.Optional;
import java.util.logging.Logger;

import seedu.taskell.commons.core.Config;
import seedu.taskell.commons.core.LogsCenter;
import seedu.taskell.commons.exceptions.DataConversionException;
import seedu.taskell.commons.util.FileUtil;

/**
 * A class to access Config stored in the hard disk as a json file
 */

public class JsonConfigStorage implements ConfigStorage {

    private static final Logger logger = LogsCenter.getLogger(JsonConfigStorage.class);
    
    private String filePath;
    
    public JsonConfigStorage(String filePath){
        this.filePath = filePath;
    }
    
    @Override
    public Optional<Config> readConfigFile() throws DataConversionException, IOException {
        return readConfig(filePath);
    }

    @Override
    public void saveConfigFile(Config config) throws IOException {
        saveConfig(config, filePath);
    }
    
    /**
     * Similar to {@link #readConfigFile()}
     * @param configFilePath location of the data. Cannot be null.
     * @throws DataConversionException if the file format is not as expected.
     */
    private Optional<Config> readConfig(String configFilePath) throws DataConversionException {
        assert configFilePath != null;

        File prefsFile = new File(configFilePath);

        if (!prefsFile.exists()) {
            logger.info("Prefs file "  + prefsFile + " not found");
            return Optional.empty();
        }

        Config config;

        try {
            config = FileUtil.deserializeObjectFromJsonFile(prefsFile, Config.class);
        } catch (IOException e) {
            logger.warning("Error reading from prefs file " + prefsFile + ": " + e);
            throw new DataConversionException(e);
        }

        return Optional.of(config);
    }

    /**
     * Similar to {@link #saveConfigFile(UserPrefs)}
     * @param configFilePath location of the data. Cannot be null.
     */
    private void saveConfig(Config config, String configFilePath) throws IOException {
        assert config != null;
        assert configFilePath != null;

        FileUtil.serializeObjectToJsonFile(new File(configFilePath), config);
    }

}
