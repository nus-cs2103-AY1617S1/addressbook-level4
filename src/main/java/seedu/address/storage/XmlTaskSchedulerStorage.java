package seedu.address.storage;

import seedu.address.commons.core.Config;
import seedu.address.commons.core.LogsCenter;
import seedu.address.commons.exceptions.DataConversionException;
import seedu.address.commons.util.ConfigUtil;
import seedu.address.commons.util.FileUtil;
import seedu.address.model.ReadOnlyTaskScheduler;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Optional;
import java.util.logging.Logger;

/**
 * A class to access TaskScheduler data stored as an xml file on the hard disk.
 */
public class XmlTaskSchedulerStorage implements TaskSchedulerStorage {

    private static final Logger logger = LogsCenter.getLogger(XmlTaskSchedulerStorage.class);

    private String filePath;

    public XmlTaskSchedulerStorage(String filePath){
        this.filePath = filePath;
    }

    public String getTaskSchedulerFilePath(){
        return filePath;
    }

    /**
     * Similar to {@link #readTaskScheduler()}
     * @param filePath location of the data. Cannot be null
     * @throws DataConversionException if the file is not in the correct format.
     */
    public Optional<ReadOnlyTaskScheduler> readTaskScheduler(String filePath) throws DataConversionException, FileNotFoundException {
        assert filePath != null;

        File taskSchedulerFile = new File(filePath);

        if (!taskSchedulerFile.exists()) {
            logger.info("TaskScheduler file "  + taskSchedulerFile + " not found");
            return Optional.empty();
        }

        ReadOnlyTaskScheduler taskSchedulerOptional = XmlFileStorage.loadDataFromSaveFile(new File(filePath));

        return Optional.of(taskSchedulerOptional);
    }

    /**
     * Similar to {@link #saveTaskScheduler(ReadOnlyTaskScheduler)}
     * @param filePath location of the data. Cannot be null
     */
    public void saveTaskScheduler(ReadOnlyTaskScheduler taskScheduler, String filePath, boolean userSet) throws IOException {
        assert taskScheduler != null;
        assert filePath != null;
        
        filePath = checkPath(filePath, userSet);
        
        File file = new File(filePath);
        FileUtil.createIfMissing(file);
        XmlFileStorage.saveDataToFile(file, new XmlSerializableTaskScheduler(taskScheduler));
    }

    /**
     * Compare updated user preference save path with config.json
     * 
     */
    
    private String checkPath(String filePath2, boolean userSet) {
        Config initializedConfig;
        String configFilePathUsed;

        configFilePathUsed = Config.DEFAULT_CONFIG_FILE;

        logger.info("Using config file : " + configFilePathUsed);

        try {
            Optional<Config> configOptional = ConfigUtil.readConfig(configFilePathUsed);
            initializedConfig = configOptional.orElse(new Config());
        } catch (DataConversionException e) {
            logger.warning("Config file at " + configFilePathUsed + " is not in the correct format. " +
                    "Using default config properties");
            initializedConfig = new Config();
        }
        if (initializedConfig.getTaskSchedulerFilePath() == filePath2) {
            return filePath2;
        }
        else {
            return initializedConfig.getTaskSchedulerFilePath();
        }
    }

    @Override
    public Optional<ReadOnlyTaskScheduler> readTaskScheduler() throws DataConversionException, IOException {
        return readTaskScheduler(filePath);
    }

    @Override
    public void saveTaskScheduler(ReadOnlyTaskScheduler taskScheduler) throws IOException {
        saveTaskScheduler(taskScheduler, filePath);
    }
}
