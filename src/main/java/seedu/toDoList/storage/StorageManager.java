package seedu.toDoList.storage;

import com.google.common.eventbus.Subscribe;

import seedu.toDoList.commons.core.ComponentManager;
import seedu.toDoList.commons.core.LogsCenter;
import seedu.toDoList.commons.events.model.TaskManagerChangedEvent;
import seedu.toDoList.commons.events.storage.DataSavingExceptionEvent;
import seedu.toDoList.commons.events.storage.RedoStoragePathChangedEvent;
import seedu.toDoList.commons.events.storage.StoragePathChangedEvent;
import seedu.toDoList.commons.events.storage.StoragePathEvent;
import seedu.toDoList.commons.events.storage.UndoStoragePathChangedEvent;
import seedu.toDoList.commons.exceptions.DataConversionException;
import seedu.toDoList.model.ReadOnlyTaskManager;
import seedu.toDoList.model.UserPrefs;

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
    public FilePath getTaskManagerPreviousFilePath() {
        return taskManagerStorage.getTaskManagerPreviousFilePath();
    }
    
    @Override
    public FilePath getTaskManagerNextFilePath() {
        return taskManagerStorage.getTaskManagerNextFilePath();
    }
    
    @Override
    public void saveTaskManagerFilePath(FilePath filePath) {
        taskManagerStorage.saveTaskManagerFilePath(filePath);
    }
    
    @Override
    public void setTaskManagerFilePath(FilePath filePath) throws IOException {
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
    
    //@@author A0146123R
    @Override
    @Subscribe
    public void handleStoragePathChangedEvent(StoragePathChangedEvent event) {
        logger.info(LogsCenter.getEventHandlingLogMessage(event, "Storage file path changed"));
        FilePath filePath = new FilePath(event.getNewStorageFilePath(), event.isToClearOld());
        changeFilePath(filePath);
        saveTaskManagerFilePath(filePath);
    }

    @Override
    @Subscribe
    public void handleUndoStoragePathChangedEvent(UndoStoragePathChangedEvent event) {
        logger.info(LogsCenter.getEventHandlingLogMessage(event, "Undo storage file path changed"));
        changeFilePath(new FilePath(getTaskManagerPreviousFilePath().getPath(), event.isToClearNew()));
    }

    @Override
    @Subscribe
    public void handleRedoStoragePathChangedEvent(RedoStoragePathChangedEvent event) {
        logger.info(LogsCenter.getEventHandlingLogMessage(event, "Redo storage file path changed"));
        changeFilePath(getTaskManagerNextFilePath());
    }

    private void changeFilePath(FilePath filePath) {
        try {
            setTaskManagerFilePath(filePath);
            raise(new StoragePathEvent(filePath.getPath()));
        } catch (IOException e) {
            raise(new DataSavingExceptionEvent(e));
        }
    }

}
