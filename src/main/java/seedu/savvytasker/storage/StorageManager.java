package seedu.savvytasker.storage;

import java.io.IOException;
import java.util.Optional;
import java.util.logging.Logger;

import com.google.common.eventbus.Subscribe;

import seedu.savvytasker.commons.core.ComponentManager;
import seedu.savvytasker.commons.core.LogsCenter;
import seedu.savvytasker.commons.events.model.SavvyTaskerChangedEvent;
import seedu.savvytasker.commons.events.storage.DataSavingExceptionEvent;
import seedu.savvytasker.commons.exceptions.DataConversionException;
import seedu.savvytasker.model.ReadOnlySavvyTasker;
import seedu.savvytasker.model.UserPrefs;

/**
 * Manages storage of AddressBook data in local storage.
 */
public class StorageManager extends ComponentManager implements Storage {

    private static final Logger logger = LogsCenter.getLogger(StorageManager.class);
    private SavvyTaskerStorage savvyTaskerStorage;
    private UserPrefsStorage userPrefsStorage;


    public StorageManager(SavvyTaskerStorage savvyTaskerStorage, UserPrefsStorage userPrefsStorage) {
        super();
        this.savvyTaskerStorage = savvyTaskerStorage;
        this.userPrefsStorage = userPrefsStorage;
    }

    public StorageManager(String savvyTaskerFilePath, String userPrefsFilePath) {
        this(new XmlSavvyTaskerStorage(savvyTaskerFilePath), new JsonUserPrefsStorage(userPrefsFilePath));
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


    // ================ AddressBook methods ==============================

    @Override
    public String getSavvyTaskerFilePath() {
        return savvyTaskerStorage.getSavvyTaskerFilePath();
    }

    @Override
    public Optional<ReadOnlySavvyTasker> readSavvyTasker() throws DataConversionException, IOException {
        return readSavvyTasker(savvyTaskerStorage.getSavvyTaskerFilePath());
    }

    @Override
    public Optional<ReadOnlySavvyTasker> readSavvyTasker(String filePath) throws DataConversionException, IOException {
        logger.fine("Attempting to read data from file: " + filePath);
        return savvyTaskerStorage.readSavvyTasker(filePath);
    }

    @Override
    public void saveSavvyTasker(ReadOnlySavvyTasker savvyTasker) throws IOException {
        saveSavvyTasker(savvyTasker, savvyTaskerStorage.getSavvyTaskerFilePath());
    }

    @Override
    public void saveSavvyTasker(ReadOnlySavvyTasker savvyTasker, String filePath) throws IOException {
        logger.fine("Attempting to write to data file: " + filePath);
        savvyTaskerStorage.saveSavvyTasker(savvyTasker, filePath);
    }


    @Override
    @Subscribe
    public void handleSavvyTaskerChangedEvent(SavvyTaskerChangedEvent event) {
        logger.info(LogsCenter.getEventHandlingLogMessage(event, "Local data changed, saving to file"));
        try {
            saveSavvyTasker(event.data);
        } catch (IOException e) {
            raise(new DataSavingExceptionEvent(e));
        }
    }

}
