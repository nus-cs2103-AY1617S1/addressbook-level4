package seedu.inbx0.storage;

import com.google.common.eventbus.Subscribe;

import seedu.inbx0.commons.core.ComponentManager;
import seedu.inbx0.commons.core.LogsCenter;
import seedu.inbx0.commons.events.model.TaskListChangedEvent;
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
    public String getTaskListFilePath() {
        return taskListStorage.getTaskListFilePath();
    }

    @Override
    public Optional<ReadOnlyTaskList> readTaskList() throws DataConversionException, IOException {
        return readTaskList(taskListStorage.getTaskListFilePath());
    }

    @Override
    public Optional<ReadOnlyTaskList> readTaskList(String filePath) throws DataConversionException, IOException {
        logger.fine("Attempting to read data from file: " + filePath);
        return taskListStorage.readTaskList(filePath);
    }

    @Override
    public void saveTaskList(ReadOnlyTaskList addressBook) throws IOException {
        saveTaskList(addressBook, taskListStorage.getTaskListFilePath());
    }

    @Override
    public void saveTaskList(ReadOnlyTaskList addressBook, String filePath) throws IOException {
        logger.fine("Attempting to write to data file: " + filePath);
        taskListStorage.saveTaskList(addressBook, filePath);
    }


    @Override
    @Subscribe
    public void handleTaskListChangedEvent(TaskListChangedEvent event) {
        logger.info(LogsCenter.getEventHandlingLogMessage(event, "Local data changed, saving to file"));
        try {
            saveTaskList(event.data);
        } catch (IOException e) {
            raise(new DataSavingExceptionEvent(e));
        }
    }

}
