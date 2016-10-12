package seedu.malitio.storage;

import com.google.common.eventbus.Subscribe;

import seedu.malitio.commons.core.ComponentManager;
import seedu.malitio.commons.core.LogsCenter;
import seedu.malitio.commons.events.model.MalitioChangedEvent;
import seedu.malitio.commons.events.storage.DataSavingExceptionEvent;
import seedu.malitio.commons.exceptions.DataConversionException;
import seedu.malitio.model.ReadOnlyMalitio;
import seedu.malitio.model.UserPrefs;

import java.io.FileNotFoundException;
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


    // ================ malitio methods ==============================

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
    public void savemalitio(ReadOnlyMalitio malitio) throws IOException {
        savemalitio(malitio, malitioStorage.getMalitioFilePath());
    }

    @Override
    public void savemalitio(ReadOnlyMalitio malitio, String filePath) throws IOException {
        logger.fine("Attempting to write to data file: " + filePath);
        malitioStorage.savemalitio(malitio, filePath);
    }


    @Override
    @Subscribe
    public void handlemalitioChangedEvent(MalitioChangedEvent event) {
        logger.info(LogsCenter.getEventHandlingLogMessage(event, "Local data changed, saving to file"));
        try {
            savemalitio(event.data);
        } catch (IOException e) {
            raise(new DataSavingExceptionEvent(e));
        }
    }

}
