package seedu.address.storage;

import com.google.common.eventbus.Subscribe;
import seedu.address.commons.core.ComponentManager;
import seedu.address.commons.core.LogsCenter;
import seedu.address.commons.events.model.AddressBookChangedEvent;
import seedu.address.commons.events.storage.DataSavingExceptionEvent;
import seedu.address.commons.exceptions.DataConversionException;
import seedu.address.model.ReadOnlyEmeraldo;
import seedu.address.model.UserPrefs;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Optional;
import java.util.logging.Logger;

/**
 * Manages storage of AddressBook data in local storage.
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


    // ================ AddressBook methods ==============================

    @Override
    public String getEmeraldoFilePath() {
        return emeraldoStorage.getEmeraldoFilePath();
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
    public void saveEmeraldo(ReadOnlyEmeraldo addressBook) throws IOException {
        saveEmeraldo(addressBook, emeraldoStorage.getEmeraldoFilePath());
    }

    @Override
    public void saveEmeraldo(ReadOnlyEmeraldo emeraldo, String filePath) throws IOException {
        logger.fine("Attempting to write to data file: " + filePath);
        emeraldoStorage.saveEmeraldo(emeraldo, filePath);
    }


    @Override
    @Subscribe
    public void handleEmeraldoChangedEvent(AddressBookChangedEvent event) {
        logger.info(LogsCenter.getEventHandlingLogMessage(event, "Local data changed, saving to file"));
        try {
            saveEmeraldo(event.data);
        } catch (IOException e) {
            raise(new DataSavingExceptionEvent(e));
        }
    }

}
