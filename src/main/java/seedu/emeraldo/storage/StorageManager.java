package seedu.emeraldo.storage;

import com.google.common.eventbus.Subscribe;

import seedu.emeraldo.commons.core.ComponentManager;
import seedu.emeraldo.commons.core.LogsCenter;
import seedu.emeraldo.commons.events.model.EmeraldoChangedEvent;
import seedu.emeraldo.commons.events.storage.DataSavingExceptionEvent;
import seedu.emeraldo.commons.exceptions.DataConversionException;
import seedu.emeraldo.model.ReadOnlyEmeraldo;
import seedu.emeraldo.model.UserPrefs;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Optional;
import java.util.logging.Logger;

/**
 * Manages storage of Emeraldo data in local storage.
 */
public class StorageManager extends ComponentManager implements Storage {

    private static final Logger logger = LogsCenter.getLogger(StorageManager.class);
    private EmeraldoStorage emeraldoStorage;
    private UserPrefsStorage userPrefsStorage;


    public StorageManager(EmeraldoStorage emeraldoStorage, UserPrefsStorage userPrefsStorage) {
        super();
        this.emeraldoStorage = emeraldoStorage;
        this.userPrefsStorage = userPrefsStorage;
    }

    public StorageManager(String emeraldoFilePath, String userPrefsFilePath) {
        this(new XmlEmeraldoStorage(emeraldoFilePath), new JsonUserPrefsStorage(userPrefsFilePath));
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


    // ================ Emeraldo methods ==============================

    @Override
    public String getEmeraldoFilePath() {
        return emeraldoStorage.getEmeraldoFilePath();
    }
    
    public void changeEmeraldoFilePath(String filepath) {
        
    }

    @Override
    public Optional<ReadOnlyEmeraldo> readEmeraldo() throws DataConversionException, IOException {
        return readEmeraldo(emeraldoStorage.getEmeraldoFilePath());
    }

    @Override
    public Optional<ReadOnlyEmeraldo> readEmeraldo(String filePath) throws DataConversionException, IOException {
        logger.fine("Attempting to read data from file: " + filePath);
        return emeraldoStorage.readEmeraldo(filePath);
    }

    @Override
    public void saveEmeraldo(ReadOnlyEmeraldo emeraldo) throws IOException {
        saveEmeraldo(emeraldo, emeraldoStorage.getEmeraldoFilePath());
    }

    @Override
    public void saveEmeraldo(ReadOnlyEmeraldo emeraldo, String filePath) throws IOException {
        logger.fine("Attempting to write to data file: " + filePath);
        emeraldoStorage.saveEmeraldo(emeraldo, filePath);
    }


    @Override
    @Subscribe
    public void handleEmeraldoChangedEvent(EmeraldoChangedEvent event) {
        logger.info(LogsCenter.getEventHandlingLogMessage(event, "Local data changed, saving to file"));
        try {
            saveEmeraldo(event.data);
        } catch (IOException e) {
            raise(new DataSavingExceptionEvent(e));
        }
    }

}
