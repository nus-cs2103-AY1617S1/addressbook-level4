package seedu.address.storage;

import com.google.common.eventbus.Subscribe;
import seedu.address.commons.core.ComponentManager;
import seedu.address.commons.core.LogsCenter;
import seedu.address.commons.events.model.AddressBookChangedEvent;
import seedu.address.commons.events.storage.DataSavingExceptionEvent;
import seedu.address.commons.exceptions.DataConversionException;
import seedu.address.model.ReadOnlyTaskScheduler;
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
    private XmlTaskSchedulerStorage addressBookStorage;
    private JsonUserPrefStorage userPrefStorage;


    public StorageManager(String addressBookFilePath, String userPrefsFilePath) {
        super();
        this.addressBookStorage = new XmlTaskSchedulerStorage(addressBookFilePath);
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


    // ================ AddressBook methods ==============================

    @Override
    public String getTaskSchedulerFilePath() {
        return addressBookStorage.getTaskSchedulerFilePath();
    }

    @Override
    public Optional<ReadOnlyTaskScheduler> readTaskScheduler() throws DataConversionException, FileNotFoundException {
        logger.fine("Attempting to read data from file: " + addressBookStorage.getTaskSchedulerFilePath());

        return addressBookStorage.readAddressBook(addressBookStorage.getTaskSchedulerFilePath());
    }

    @Override
    public void saveTaskScheduler(ReadOnlyTaskScheduler addressBook) throws IOException {
        addressBookStorage.saveTaskScheduler(addressBook, addressBookStorage.getTaskSchedulerFilePath());
    }


    @Override
    @Subscribe
    public void handleAddressBookChangedEvent(AddressBookChangedEvent event) {
        logger.info(LogsCenter.getEventHandlingLogMessage(event, "Local data changed, saving to file"));
        try {
            saveTaskScheduler(event.data);
        } catch (IOException e) {
            raise(new DataSavingExceptionEvent(e));
        }
    }

}
