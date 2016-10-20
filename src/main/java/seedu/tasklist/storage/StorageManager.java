package seedu.tasklist.storage;

import com.google.common.eventbus.Subscribe;

import seedu.tasklist.commons.core.ComponentManager;
import seedu.tasklist.commons.core.LogsCenter;
import seedu.tasklist.commons.events.model.TaskListChangedEvent;
import seedu.tasklist.commons.events.storage.DataSavingExceptionEvent;
import seedu.tasklist.commons.exceptions.DataConversionException;
import seedu.tasklist.model.ReadOnlyTaskList;
import seedu.tasklist.model.UserPrefs;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Optional;
import java.util.logging.Logger;

/**
 * Manages storage of TaskList data in local storage.
 */
public class StorageManager extends ComponentManager implements Storage {

    private static final Logger logger = LogsCenter.getLogger(StorageManager.class);
    private XmlTaskListStorage taskListStorage;
    private JsonUserPrefStorage userPrefStorage;


    public StorageManager(String taskListFilePath, String userPrefsFilePath) {
        super();
        this.taskListStorage = new XmlTaskListStorage(taskListFilePath);
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


    // ================ TaskList methods ==============================

    @Override
    public String getTaskListFilePath() {
        return taskListStorage.getTaskListFilePath();
    }

    @Override
    public Optional<ReadOnlyTaskList> readTaskList() throws DataConversionException, FileNotFoundException {
        logger.fine("Attempting to read data from file: " + taskListStorage.getTaskListFilePath());

        return taskListStorage.readTaskList(taskListStorage.getTaskListFilePath());
    }

    @Override
    public void saveTaskList(ReadOnlyTaskList taskList) throws IOException {
        taskListStorage.saveTaskList(taskList, taskListStorage.getTaskListFilePath());
    }


    @Override
    @Subscribe
    public void handleTaskListEvent(TaskListChangedEvent event) {
        logger.info(LogsCenter.getEventHandlingLogMessage(event, "Local data changed, saving to file"));
        try {
            saveTaskList(event.data);
        } catch (IOException e) {
            raise(new DataSavingExceptionEvent(e));
        }
    }

	@Override
	public void setTaskListFilePath(String filepath) {
		 this.taskListStorage = new XmlTaskListStorage(filepath);		
	}

}
