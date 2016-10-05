package seedu.smartscheduler.storage;

import com.google.common.eventbus.Subscribe;

import seedu.smartscheduler.commons.core.ComponentManager;
import seedu.smartscheduler.commons.core.LogsCenter;
import seedu.smartscheduler.commons.events.model.TaskListChangedEvent;
import seedu.smartscheduler.commons.events.storage.DataSavingExceptionEvent;
import seedu.smartscheduler.commons.exceptions.DataConversionException;
import seedu.smartscheduler.model.ReadOnlyTaskList;
import seedu.smartscheduler.model.UserPrefs;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Optional;
import java.util.logging.Logger;

/**
 * Manages storage of AddressBook data in local storage.
 */
public class StorageManager extends ComponentManager implements Storage {

    private static final Logger logger = LogsCenter.getLogger(StorageManager.class);
    private XmlTaskListStorage addressBookStorage;
    private JsonUserPrefStorage userPrefStorage;


    public StorageManager(String addressBookFilePath, String userPrefsFilePath) {
        super();
        this.addressBookStorage = new XmlTaskListStorage(addressBookFilePath);
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
    public String getAddressBookFilePath() {
        return addressBookStorage.getAddressBookFilePath();
    }

    @Override
    public Optional<ReadOnlyTaskList> readAddressBook() throws DataConversionException, FileNotFoundException {
        logger.fine("Attempting to read data from file: " + addressBookStorage.getAddressBookFilePath());

        return addressBookStorage.readAddressBook(addressBookStorage.getAddressBookFilePath());
    }

    @Override
    public void saveAddressBook(ReadOnlyTaskList addressBook) throws IOException {
        addressBookStorage.saveAddressBook(addressBook, addressBookStorage.getAddressBookFilePath());
    }


    @Override
    @Subscribe
    public void handleAddressBookChangedEvent(TaskListChangedEvent event) {
        logger.info(LogsCenter.getEventHandlingLogMessage(event, "Local data changed, saving to file"));
        try {
            saveAddressBook(event.data);
        } catch (IOException e) {
            raise(new DataSavingExceptionEvent(e));
        }
    }

}
