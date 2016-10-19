package seedu.jimi.storage;

import java.io.IOException;
import java.util.Optional;
import java.util.logging.Logger;

import com.google.common.eventbus.Subscribe;

import seedu.jimi.commons.core.ComponentManager;
import seedu.jimi.commons.core.LogsCenter;
import seedu.jimi.commons.events.model.AddressBookChangedEvent;
import seedu.jimi.commons.events.storage.DataSavingExceptionEvent;
import seedu.jimi.commons.events.storage.StoragePathChangedEvent;
import seedu.jimi.commons.exceptions.DataConversionException;
import seedu.jimi.model.ReadOnlyTaskBook;
import seedu.jimi.model.TaskBook;
import seedu.jimi.model.UserPrefs;

/**
 * Manages storage of TaskBook data in local storage.
 */
public class StorageManager extends ComponentManager implements Storage {

    private static final Logger logger = LogsCenter.getLogger(StorageManager.class);
    private TaskBookStorage taskBookStorage;
    private UserPrefsStorage userPrefsStorage;


    public StorageManager(TaskBookStorage taskBookStorage, UserPrefsStorage userPrefsStorage) {
        super();
        this.taskBookStorage = taskBookStorage;
        this.userPrefsStorage = userPrefsStorage;
    }

    public StorageManager(String addressBookFilePath, String userPrefsFilePath) {
        this(new XmlTaskBookStorage(addressBookFilePath), new JsonUserPrefsStorage(userPrefsFilePath));
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


    // ================ TaskBook methods ==============================

    @Override
    public String getTaskBookFilePath() {
        return taskBookStorage.getTaskBookFilePath();
    }

    @Override
    public Optional<ReadOnlyTaskBook> readTaskBook() throws DataConversionException, IOException {
        return readTaskBook(taskBookStorage.getTaskBookFilePath());
    }

    @Override
    public Optional<ReadOnlyTaskBook> readTaskBook(String filePath) throws DataConversionException, IOException {
        logger.fine("Attempting to read data from file: " + filePath);
        return taskBookStorage.readTaskBook(filePath);
    }

    @Override
    public void saveTaskBook(ReadOnlyTaskBook taskBook) throws IOException {
        saveTaskBook(taskBook, taskBookStorage.getTaskBookFilePath());
    }

    @Override
    public void saveTaskBook(ReadOnlyTaskBook taskBook, String filePath) throws IOException {
        logger.fine("Attempting to write to data file: " + filePath);
        taskBookStorage.saveTaskBook(taskBook, filePath);
    }


    @Override
    @Subscribe
    public void handleAddressBookChangedEvent(AddressBookChangedEvent event) {
        logger.info(LogsCenter.getEventHandlingLogMessage(event, "Local data changed, saving to file"));
        try {
            saveTaskBook(event.data);
        } catch (IOException e) {
            raise(new DataSavingExceptionEvent(e));
        }
    }
    
    @Subscribe
    public void handleStoragePathChangedEvent(StoragePathChangedEvent spce) {
        logger.info(LogsCenter.getEventHandlingLogMessage(spce, "Storage file path changed"));
        try {
            // copying previous data from old save file to new save file
            ReadOnlyTaskBook oldTaskBook = readTaskBook(spce.oldPath).orElse(new TaskBook());
            ((XmlTaskBookStorage) taskBookStorage).setTaskBookFilePath(spce.newPath);
            saveTaskBook(oldTaskBook);
        } catch (IOException  | DataConversionException e) {
            raise(new DataSavingExceptionEvent(e));
        } 
    }
}
