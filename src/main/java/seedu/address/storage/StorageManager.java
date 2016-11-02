package seedu.address.storage;

import com.google.common.eventbus.Subscribe;
import seedu.address.commons.core.ComponentManager;
import seedu.address.commons.core.LogsCenter;
import seedu.address.commons.events.model.AliasManagerChangedEvent;
import seedu.address.commons.events.model.TaskManagerChangedEvent;
import seedu.address.commons.events.storage.DataSavingExceptionEvent;
import seedu.address.commons.exceptions.DataConversionException;
import seedu.address.model.ReadOnlyAliasManager;
import seedu.address.model.ReadOnlyTaskManager;
import seedu.address.model.UserPrefs;

import java.io.IOException;
import java.util.Optional;
import java.util.logging.Logger;

/**
 * Manages storage of TaskManager data in local storage.
 */
public class StorageManager extends ComponentManager implements Storage {

    private static final Logger logger = LogsCenter.getLogger(StorageManager.class);
    private TaskManagerStorage addressBookStorage;
    private UserPrefsStorage userPrefsStorage;
    private AliasManagerStorage aliasManagerStorage;

    public StorageManager(TaskManagerStorage addressBookStorage, UserPrefsStorage userPrefsStorage, AliasManagerStorage aliasManagerStorage) {
        super();
        this.addressBookStorage = addressBookStorage;
        this.userPrefsStorage = userPrefsStorage;
        this.aliasManagerStorage = aliasManagerStorage;
    }

    public StorageManager(String addressBookFilePath, String userPrefsFilePath, String aliasManagerFilePath) {
        this(new XmlTaskManagerStorage(addressBookFilePath), new JsonUserPrefsStorage(userPrefsFilePath), new XmlAliasManagerStorage(aliasManagerFilePath));
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


    // ================ TaskManager methods ==============================

    @Override
    public String getTaskManagerFilePath() {
        return addressBookStorage.getTaskManagerFilePath();
    }

    @Override
    public Optional<ReadOnlyTaskManager> readTaskManager() throws DataConversionException, IOException {
        return readTaskManager(addressBookStorage.getTaskManagerFilePath());
    }

    @Override
    public Optional<ReadOnlyTaskManager> readTaskManager(String filePath) throws DataConversionException, IOException {
        logger.fine("Attempting to read data from file: " + filePath);
        return addressBookStorage.readTaskManager(filePath);
    }

    @Override
    public void saveTaskManager(ReadOnlyTaskManager addressBook) throws IOException {
        saveTaskManager(addressBook, addressBookStorage.getTaskManagerFilePath());
    }

    @Override
    public void saveTaskManager(ReadOnlyTaskManager addressBook, String filePath) throws IOException {
        logger.fine("Attempting to write to data file: " + filePath);
        addressBookStorage.saveTaskManager(addressBook, filePath);
    }

    @Override
    @Subscribe
    public void handleTaskManagerChangedEvent(TaskManagerChangedEvent event) {
        logger.info(LogsCenter.getEventHandlingLogMessage(event, "Local task manager data changed, saving to file"));
        try {
            saveTaskManager(event.data);
        } catch (IOException e) {
            raise(new DataSavingExceptionEvent(e));
        }
    }
    
    // ================ AliasManager methods ==============================
    
    @Override
    public String getAliasManagerFilePath() {
        return aliasManagerStorage.getAliasManagerFilePath();
    }

    @Override
    public Optional<ReadOnlyAliasManager> readAliasManager() throws DataConversionException, IOException {
        return readAliasManager(aliasManagerStorage.getAliasManagerFilePath());
    }

    @Override
    public Optional<ReadOnlyAliasManager> readAliasManager(String filePath) throws DataConversionException, IOException {
        logger.fine("Attempting to read data from file: " + filePath);
        return aliasManagerStorage.readAliasManager(filePath);
    }

    @Override
    public void saveAliasManager(ReadOnlyAliasManager aliasManager) throws IOException {
        saveAliasManager(aliasManager, aliasManagerStorage.getAliasManagerFilePath());
    }

    @Override
    public void saveAliasManager(ReadOnlyAliasManager aliasManager, String filePath) throws IOException {
        logger.fine("Attempting to write to data file: " + filePath);
        aliasManagerStorage.saveAliasManager(aliasManager, filePath);
    }

    @Override
    @Subscribe
    public void handleAliasManagerChangedEvent(AliasManagerChangedEvent event) {
        logger.info(LogsCenter.getEventHandlingLogMessage(event, "Local alias manager data changed, saving to file"));
        try {
            saveAliasManager(event.data);
        } catch (IOException e) {
            raise(new DataSavingExceptionEvent(e));
        }
    }
}
