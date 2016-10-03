package tars.storage;

import com.google.common.eventbus.Subscribe;

import tars.commons.core.ComponentManager;
import tars.commons.core.LogsCenter;
import tars.commons.events.model.TarsChangedEvent;
import tars.commons.events.storage.DataSavingExceptionEvent;
import tars.commons.exceptions.DataConversionException;
import tars.model.ReadOnlyTars;
import tars.model.UserPrefs;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Optional;
import java.util.logging.Logger;

/**
 * Manages storage of Tars data in local storage.
 */
public class StorageManager extends ComponentManager implements Storage {

    private static final Logger logger = LogsCenter.getLogger(StorageManager.class);
    private XmlTarsStorage addressBookStorage;
    private JsonUserPrefStorage userPrefStorage;


    public StorageManager(String addressBookFilePath, String userPrefsFilePath) {
        super();
        this.addressBookStorage = new XmlTarsStorage(addressBookFilePath);
        this.userPrefStorage = new JsonUserPrefStorage(userPrefsFilePath);
    }

    // ================ UserPrefs methods ==============================

    @Override
    public Optional<UserPrefs> readUserPrefs() throws DataConversionException, IOException {
        return userPrefStorage.readUserPrefs();
    }

    @Override
    public void saveUserPrefs(UserPrefs userPrefs) throws IOException {
        userPrefStorage.saveUserPrefs(userPrefs);
    }


    // ================ Tars methods ==============================

    @Override
    public String getTarsFilePath() {
        return addressBookStorage.getTarsFilePath();
    }

    @Override
    public Optional<ReadOnlyTars> readTars() throws DataConversionException, FileNotFoundException {
        logger.fine("Attempting to read data from file: " + addressBookStorage.getTarsFilePath());

        return addressBookStorage.readTars(addressBookStorage.getTarsFilePath());
    }

    @Override
    public void saveTars(ReadOnlyTars addressBook) throws IOException {
        addressBookStorage.saveTars(addressBook, addressBookStorage.getTarsFilePath());
    }


    @Override
    @Subscribe
    public void handleTarsChangedEvent(TarsChangedEvent event) {
        logger.info(LogsCenter.getEventHandlingLogMessage(event, "Local data changed, saving to file"));
        try {
            saveTars(event.data);
        } catch (IOException e) {
            raise(new DataSavingExceptionEvent(e));
        }
    }

}
