package seedu.malitio.storage;

import com.google.common.eventbus.Subscribe;

import seedu.malitio.commons.core.ComponentManager;
import seedu.malitio.commons.core.LogsCenter;
import seedu.malitio.commons.events.model.MalitioChangedEvent;
import seedu.malitio.commons.events.storage.DataSavingExceptionEvent;
import seedu.malitio.commons.events.storage.DataStorageFileChangedEvent;
import seedu.malitio.commons.exceptions.DataConversionException;
import seedu.malitio.commons.util.FileUtil;
import seedu.malitio.model.ReadOnlyMalitio;
import seedu.malitio.model.UserPrefs;

import java.io.IOException;
import java.util.Optional;
import java.util.logging.Logger;

/**
 * Manages storage of Malitio data in local storage.
 */
public class StorageManager extends ComponentManager implements Storage {

    private static final Logger logger = LogsCenter.getLogger(StorageManager.class);
    private MalitioStorage malitioStorage;
    private UserPrefsStorage userPrefsStorage;

    public StorageManager(MalitioStorage malitioStorage, UserPrefsStorage userPrefsStorage) {
        super();
        this.malitioStorage = malitioStorage;
        this.userPrefsStorage = userPrefsStorage;
    }

    public StorageManager(String malitioFilePath, String userPrefsFilePath) {
        this(new XmlMalitioStorage(malitioFilePath), new JsonUserPrefsStorage(userPrefsFilePath));
    }

    // ================ UserPrefs methods ==============================

    @Override
    public Optional<UserPrefs> readUserPrefs() throws DataConversionException, IOException {
        return userPrefsStorage.readUserPrefs();
    }

    @Override
    public void saveUserPrefs(UserPrefs userPrefs) throws IOException {
        userPrefsStorage.saveUserPrefs(userPrefs);
    }


    // ================ Malitio methods ==============================

    @Override
    public String getMalitioFilePath() {
        return malitioStorage.getMalitioFilePath();
    }

    @Override
    public Optional<ReadOnlyMalitio> readMalitio() throws DataConversionException, IOException {
        return readMalitio(malitioStorage.getMalitioFilePath());
    }

    @Override
    public Optional<ReadOnlyMalitio> readMalitio(String filePath) throws DataConversionException, IOException {
        logger.fine("Attempting to read data from file: " + filePath);
        return malitioStorage.readMalitio(filePath);
    }

    @Override
    public void saveMalitio(ReadOnlyMalitio malitio) throws IOException {
        saveMalitio(malitio, malitioStorage.getMalitioFilePath());
    }

    @Override
    public void saveMalitio(ReadOnlyMalitio malitio, String filePath) throws IOException {
        logger.fine("Attempting to write to data file: " + filePath);
        malitioStorage.saveMalitio(malitio, filePath);
    }


    @Override
    @Subscribe
    public void handleMalitioChangedEvent(MalitioChangedEvent event) {
        logger.info(LogsCenter.getEventHandlingLogMessage(event, "Local data changed, saving to file"));
        try {
            saveMalitio(event.data, malitioStorage.getMalitioFilePath());
        } catch (IOException e) {
            raise(new DataSavingExceptionEvent(e));
        }
    }
    
    /**
     * Stores the current data file in the new directory and deletes the old data file.
     * @param event
     * @throws DataConversionException
     */
    //@@author a0126633j
    @Subscribe
    public void handleDataStorageFileChangedEvent(DataStorageFileChangedEvent event) throws DataConversionException {
        logger.info(LogsCenter.getEventHandlingLogMessage(event, "Data storage file path changed, updating.."));
        String oldDataFilePath = malitioStorage.getMalitioFilePath();
        malitioStorage = new XmlMalitioStorage(event.dataFilePath);
        
        if(oldDataFilePath != this.malitioStorage.getMalitioFilePath()) {
            return;
        }
        
        try {
            saveMalitio(readMalitio(oldDataFilePath).get(), this.malitioStorage.getMalitioFilePath()); 
        } catch (IOException e) {
            raise(new DataSavingExceptionEvent(e));
        }
        
        try {
            logger.info(LogsCenter.getEventHandlingLogMessage(event, "Old data file is being deleted."));
            FileUtil.deleteFile(oldDataFilePath);
        } catch (IOException e) {
            logger.info(LogsCenter.getEventHandlingLogMessage(event, "Failed to delete old data file."));
        }
    }
}
