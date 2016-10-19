package seedu.jimi.storage;

import com.google.common.eventbus.Subscribe;

import seedu.jimi.commons.core.ComponentManager;
import seedu.jimi.commons.core.Config;
import seedu.jimi.commons.core.LogsCenter;
import seedu.jimi.commons.events.model.AddressBookChangedEvent;
import seedu.jimi.commons.events.storage.DataSavingExceptionEvent;
import seedu.jimi.commons.events.storage.StoragePathChangedEvent;
import seedu.jimi.commons.exceptions.DataConversionException;
import seedu.jimi.commons.util.ConfigUtil;
import seedu.jimi.model.ReadOnlyTaskBook;
import seedu.jimi.model.UserPrefs;

import java.io.IOException;
import java.util.Optional;
import java.util.logging.Logger;

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
        this(new XmlAddressBookStorage(addressBookFilePath), new JsonUserPrefsStorage(userPrefsFilePath));
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
    public String getAddressBookFilePath() {
        return taskBookStorage.getAddressBookFilePath();
    }

    @Override
    public Optional<ReadOnlyTaskBook> readTaskBook() throws DataConversionException, IOException {
        return readTaskBook(taskBookStorage.getAddressBookFilePath());
    }

    @Override
    public Optional<ReadOnlyTaskBook> readTaskBook(String filePath) throws DataConversionException, IOException {
        logger.fine("Attempting to read data from file: " + filePath);
        return taskBookStorage.readTaskBook(filePath);
    }

    @Override
    public void saveTaskBook(ReadOnlyTaskBook taskBook) throws IOException {
        saveTaskBook(taskBook, taskBookStorage.getAddressBookFilePath());
    }

    @Override
    public void saveTaskBook(ReadOnlyTaskBook taskBook, String filePath) throws IOException {
        logger.fine("Attempting to write to data file: " + filePath);
        taskBookStorage.saveTaskBook(taskBook, filePath);
        indicateStoragePathChanged();
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
            ConfigUtil.saveConfig(spce.data, Config.DEFAULT_CONFIG_FILE);
        } catch (IOException e) {
            raise(new DataSavingExceptionEvent(e));
        }
    }
    
    /** Raises an event to indicate the model has changed */
    private void indicateStoragePathChanged() {
        Config config;
        try {
            config = ConfigUtil.readConfig(Config.DEFAULT_CONFIG_FILE).orElse(new Config());
        } catch (DataConversionException e) {
            config = new Config();
        }
        raise(new StoragePathChangedEvent(config));
    }

}
