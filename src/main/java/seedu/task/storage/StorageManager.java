package seedu.task.storage;

import com.google.common.eventbus.Subscribe;

import seedu.task.commons.core.ComponentManager;
import seedu.task.commons.core.LogsCenter;
import seedu.task.commons.events.model.TaskManagerChangedEvent;
import seedu.task.commons.events.storage.DataSavingExceptionEvent;
import seedu.task.commons.exceptions.DataConversionException;
import seedu.task.model.ReadOnlyTaskManager;
import seedu.task.model.UserPrefs;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Optional;
import java.util.logging.Logger;

/**
 * Manages storage of AddressBook data in local storage.
 */
public class StorageManager extends ComponentManager implements Storage {

    private static final Logger logger = LogsCenter.getLogger(StorageManager.class);
    private TaskManagerStorage taskManagerStorage;
    private UserPrefsStorage userPrefsStorage;


    public StorageManager(TaskManagerStorage taskManagerStorage, UserPrefsStorage userPrefsStorage) {
        super();
        this.taskManagerStorage = taskManagerStorage;
        this.userPrefsStorage = userPrefsStorage;
    }

    public StorageManager(String addressBookFilePath, String userPrefsFilePath) {
        this(new XmlTaskManagerStorage(addressBookFilePath), new JsonUserPrefsStorage(userPrefsFilePath));
    }

    // ================ UserPrefs methods ==============================

    @Override
    public Optional<UserPrefs> readUserPrefs() throws DataConversionException, IOException, FileNotFoundException {
        return userPrefsStorage.readUserPrefs();
    }

    @Override
    public void saveUserPrefs(UserPrefs userPrefs) throws IOException, FileNotFoundException {
        userPrefsStorage.saveUserPrefs(userPrefs);
    }


    // ================ AddressBook methods ==============================

    @Override
    public String getAddressBookFilePath() {
        return taskManagerStorage.getAddressBookFilePath();
    }

    @Override
    public Optional<ReadOnlyTaskManager> readAddressBook() throws DataConversionException, IOException, FileNotFoundException {
        return readAddressBook(taskManagerStorage.getAddressBookFilePath());
    }

    @Override
    public Optional<ReadOnlyTaskManager> readAddressBook(String filePath) throws DataConversionException, IOException, FileNotFoundException {
        logger.fine("Attempting to read data from file: " + filePath);
        return taskManagerStorage.readAddressBook(filePath);
    }

    @Override
    public void saveAddressBook(ReadOnlyTaskManager addressBook) throws IOException, FileNotFoundException {
        saveAddressBook(addressBook, taskManagerStorage.getAddressBookFilePath());
    }

    @Override
    public void saveAddressBook(ReadOnlyTaskManager addressBook, String filePath) throws IOException, FileNotFoundException {
        logger.fine("Attempting to write to data file: " + filePath);
        taskManagerStorage.saveAddressBook(addressBook, filePath);
    }


    @Override
    @Subscribe
    public void handleAddressBookChangedEvent(TaskManagerChangedEvent event) {
        logger.info(LogsCenter.getEventHandlingLogMessage(event, "Local data changed, saving to file"));
        try {
            saveAddressBook(event.data);
        } catch (IOException e) {
            raise(new DataSavingExceptionEvent(e));
        }
    }

}
