package seedu.inbx0.storage;

import com.google.common.eventbus.Subscribe;

import seedu.inbx0.commons.core.ComponentManager;
import seedu.inbx0.commons.core.LogsCenter;
import seedu.inbx0.commons.events.model.AddressBookChangedEvent;
import seedu.inbx0.commons.events.storage.DataSavingExceptionEvent;
import seedu.inbx0.commons.exceptions.DataConversionException;
import seedu.inbx0.model.ReadOnlyTaskList;
import seedu.inbx0.model.UserPrefs;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Optional;
import java.util.logging.Logger;

/**
 * Manages storage of TaskList data in local storage.
 */
public class StorageManager extends ComponentManager implements Storage {

    private static final Logger logger = LogsCenter.getLogger(StorageManager.class);
    private TaskListStorage taskListStorage;
    private UserPrefsStorage userPrefsStorage;


    public StorageManager(TaskListStorage taskListStorage, UserPrefsStorage userPrefsStorage) {
        super();
        this.taskListStorage = taskListStorage;
        this.userPrefsStorage = userPrefsStorage;
    }

    public StorageManager(String addressBookFilePath, String userPrefsFilePath) {
        this(new XmlTaskListStorage(addressBookFilePath), new JsonUserPrefsStorage(userPrefsFilePath));
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


    // ================ TaskList methods ==============================

    @Override
    public String getAddressBookFilePath() {
        return taskListStorage.getAddressBookFilePath();
    }

    @Override
    public Optional<ReadOnlyTaskList> readAddressBook() throws DataConversionException, IOException {
        return readAddressBook(taskListStorage.getAddressBookFilePath());
    }

    @Override
    public Optional<ReadOnlyTaskList> readAddressBook(String filePath) throws DataConversionException, IOException {
        logger.fine("Attempting to read data from file: " + filePath);
        return taskListStorage.readAddressBook(filePath);
    }

    @Override
    public void saveAddressBook(ReadOnlyTaskList addressBook) throws IOException {
        saveAddressBook(addressBook, taskListStorage.getAddressBookFilePath());
    }

    @Override
    public void saveAddressBook(ReadOnlyTaskList addressBook, String filePath) throws IOException {
        logger.fine("Attempting to write to data file: " + filePath);
        taskListStorage.saveAddressBook(addressBook, filePath);
    }


    @Override
    @Subscribe
    public void handleAddressBookChangedEvent(AddressBookChangedEvent event) {
        logger.info(LogsCenter.getEventHandlingLogMessage(event, "Local data changed, saving to file"));
        try {
            saveAddressBook(event.data);
        } catch (IOException e) {
            raise(new DataSavingExceptionEvent(e));
        }
    }

}
