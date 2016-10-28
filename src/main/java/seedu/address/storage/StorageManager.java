package seedu.address.storage;

import com.google.common.eventbus.Subscribe;
import seedu.address.commons.core.ComponentManager;
import seedu.address.commons.core.EventsCenter;
import seedu.address.commons.core.LogsCenter;
import seedu.address.commons.events.model.TaskManagerChangedEvent;
import seedu.address.commons.events.storage.DataSavingExceptionEvent;
import seedu.address.commons.events.storage.RedoStoragePathChangedEvent;
import seedu.address.commons.events.storage.StoragePathChangedBackEvent;
import seedu.address.commons.events.storage.StoragePathChangedEvent;
import seedu.address.commons.exceptions.DataConversionException;
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
    private TaskManagerStorage taskManagerStorage;
    private UserPrefsStorage userPrefsStorage;

    public StorageManager(TaskManagerStorage taskManagerStorage, UserPrefsStorage userPrefsStorage) {
        super();
        this.taskManagerStorage = taskManagerStorage;
        this.userPrefsStorage = userPrefsStorage;
    }

    public StorageManager(String taskManagerFilePath, String userPrefsFilePath) {
        this(new XmlTaskManagerStorage(taskManagerFilePath), new JsonUserPrefsStorage(userPrefsFilePath));
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
        return taskManagerStorage.getTaskManagerFilePath();
    }
    
    //@@author A0146123R
    @Override
    public String getTaskManagerPreviousFilePath() {
        return taskManagerStorage.getTaskManagerPreviousFilePath();
    }
    
    
    @Override
    public void setTaskManagerFilePath(String filePath) {
        taskManagerStorage.setTaskManagerFilePath(filePath);
    }
    //@@author

    @Override
    public Optional<ReadOnlyTaskManager> readTaskManager() throws DataConversionException, IOException {
        return readTaskManager(taskManagerStorage.getTaskManagerFilePath());
    }

    @Override
    public Optional<ReadOnlyTaskManager> readTaskManager(String filePath) throws DataConversionException, IOException {
        logger.fine("Attempting to read data from file: " + filePath);
        return taskManagerStorage.readTaskManager(filePath);
    }

    @Override
    public void saveTaskManager(ReadOnlyTaskManager taskManager) throws IOException {
        saveTaskManager(taskManager, taskManagerStorage.getTaskManagerFilePath());
    }

    @Override
    public void saveTaskManager(ReadOnlyTaskManager taskManager, String filePath) throws IOException {
        logger.fine("Attempting to write to data file: " + filePath);
        taskManagerStorage.saveTaskManager(taskManager, filePath);
    }
    
    //@@author A0146123R
    @Override
    public void deleteTaskManager() throws IOException {
        deleteTaskManager(taskManagerStorage.getTaskManagerFilePath());
    }
    
    @Override
    public void deleteTaskManager(String filePath) throws IOException {
        logger.fine("Attempting to delete the data file: " + filePath);
        taskManagerStorage.deleteTaskManager(filePath);
    }

    @Override
    @Subscribe
    public void handleTaskManagerChangedEvent(TaskManagerChangedEvent event) {
        logger.info(LogsCenter.getEventHandlingLogMessage(event, "Local data changed, saving to file"));
        try {
            saveTaskManager(event.data);
        } catch (IOException e) {
            raise(new DataSavingExceptionEvent(e));
        }
    }
    
    @Override
    @Subscribe
    public void handleStoragePathChangedEvent(StoragePathChangedEvent event) {
        logger.info(LogsCenter.getEventHandlingLogMessage(event, "Storage file path changed, saving to new file"));
        try {
            if (event.isToClearOld) {
                deleteTaskManager(getTaskManagerFilePath());
            }
            setTaskManagerFilePath(event.newStorageFilePath);
        } catch (IOException e) {
            raise(new DataSavingExceptionEvent(e));
        }
    }
    
    @Override
    @Subscribe
    public void handleStoragePathChangedBackEvent(StoragePathChangedBackEvent event) {
        logger.info(LogsCenter.getEventHandlingLogMessage(event, "Storage file path changed back"));
        EventsCenter.getInstance().post(new StoragePathChangedEvent(getTaskManagerPreviousFilePath(), event.isToClearNew));
    }
    
    @Override
    @Subscribe
    public void handleRedoStoragePathChangedEvent(RedoStoragePathChangedEvent event) {
        logger.info(LogsCenter.getEventHandlingLogMessage(event, "Redo storage file path changed"));
        EventsCenter.getInstance().post(new StoragePathChangedEvent(getTaskManagerPreviousFilePath(), event.isToClearOld));
    }

}
