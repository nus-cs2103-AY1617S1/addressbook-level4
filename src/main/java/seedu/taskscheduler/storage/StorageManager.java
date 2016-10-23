package seedu.taskscheduler.storage;

import com.google.common.eventbus.Subscribe;

import seedu.taskscheduler.commons.core.ComponentManager;
import seedu.taskscheduler.commons.core.LogsCenter;
import seedu.taskscheduler.commons.events.model.TaskSchedulerChangedEvent;
import seedu.taskscheduler.commons.events.storage.DataSavingExceptionEvent;
import seedu.taskscheduler.commons.exceptions.DataConversionException;
import seedu.taskscheduler.model.ReadOnlyTaskScheduler;
import seedu.taskscheduler.model.UserPrefs;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Optional;
import java.util.logging.Logger;

/**
 * Manages storage of Task Scheduler data in local storage.
 */
public class StorageManager extends ComponentManager implements Storage {

    private static final Logger logger = LogsCenter.getLogger(StorageManager.class);
    private XmlTaskSchedulerStorage taskSchedulerStorage;
    private JsonUserPrefStorage userPrefStorage;


    public StorageManager(String taskSchedulerFilePath, String userPrefsFilePath) {
        super();
        this.taskSchedulerStorage = new XmlTaskSchedulerStorage(taskSchedulerFilePath);
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


    // ================ TaskScheduler methods ==============================

    @Override
    public String getTaskSchedulerFilePath() {
        return taskSchedulerStorage.getTaskSchedulerFilePath();
    }

    @Override
    public void setTaskSchedulerFilePath(String newPath) {
        this.taskSchedulerStorage = new XmlTaskSchedulerStorage(newPath);
    }
    
    @Override
    public Optional<ReadOnlyTaskScheduler> readTaskScheduler() throws DataConversionException, FileNotFoundException {
        logger.fine("Attempting to read data from file: " + taskSchedulerStorage.getTaskSchedulerFilePath());

        return taskSchedulerStorage.readTaskScheduler(taskSchedulerStorage.getTaskSchedulerFilePath());
    }

    @Override
    public void saveTaskScheduler(ReadOnlyTaskScheduler taskScheduler) throws IOException {
        taskSchedulerStorage.saveTaskScheduler(taskScheduler, taskSchedulerStorage.getTaskSchedulerFilePath());
    }


    @Override
    @Subscribe
    public void handleTaskSchedulerChangedEvent(TaskSchedulerChangedEvent event) {
        logger.info(LogsCenter.getEventHandlingLogMessage(event, "Local data changed, saving to file"));
        try {
            saveTaskScheduler(event.data);
        } catch (IOException e) {
            raise(new DataSavingExceptionEvent(e));
        }
    }

}
