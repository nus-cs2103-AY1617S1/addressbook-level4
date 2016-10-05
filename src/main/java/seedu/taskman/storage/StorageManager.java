package seedu.taskman.storage;

import com.google.common.eventbus.Subscribe;
import seedu.taskman.commons.core.ComponentManager;
import seedu.taskman.commons.core.LogsCenter;
import seedu.taskman.commons.events.model.TaskManChangedEvent;
import seedu.taskman.commons.events.storage.DataSavingExceptionEvent;
import seedu.taskman.commons.exceptions.DataConversionException;
import seedu.taskman.model.ReadOnlyTaskMan;
import seedu.taskman.model.UserPrefs;

import java.io.IOException;
import java.util.Optional;
import java.util.logging.Logger;

/**
 * Manages storage of TaskMan data in local storage.
 */
public class StorageManager extends ComponentManager implements Storage {

    private static final Logger logger = LogsCenter.getLogger(StorageManager.class);
    private TaskManStorage taskManStorage;
    private UserPrefsStorage userPrefsStorage;


    public StorageManager(TaskManStorage taskManStorage, UserPrefsStorage userPrefsStorage) {
        super();
        this.taskManStorage = taskManStorage;
        this.userPrefsStorage = userPrefsStorage;
    }

    public StorageManager(String taskManFilePath, String userPrefsFilePath) {
        this(new XmlTaskManStorage(taskManFilePath), new JsonUserPrefsStorage(userPrefsFilePath));
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


    // ================ TaskMan methods ==============================

    @Override
    public String getTaskManFilePath() {
        return taskManStorage.getTaskManFilePath();
    }

    @Override
    public Optional<ReadOnlyTaskMan> readTaskMan() throws DataConversionException, IOException {
        return readTaskMan(taskManStorage.getTaskManFilePath());
    }

    @Override
    public Optional<ReadOnlyTaskMan> readTaskMan(String filePath) throws DataConversionException, IOException {
        logger.fine("Attempting to read data from file: " + filePath);
        return taskManStorage.readTaskMan(filePath);
    }

    @Override
    public void saveTaskMan(ReadOnlyTaskMan taskMan) throws IOException {
        saveTaskMan(taskMan, taskManStorage.getTaskManFilePath());
    }

    @Override
    public void saveTaskMan(ReadOnlyTaskMan taskMan, String filePath) throws IOException {
        logger.fine("Attempting to write to data file: " + filePath);
        taskManStorage.saveTaskMan(taskMan, filePath);
    }


    @Override
    @Subscribe
    public void handleTaskManChangedEvent(TaskManChangedEvent event) {
        logger.info(LogsCenter.getEventHandlingLogMessage(event, "Local data changed, saving to file"));
        try {
            saveTaskMan(event.data);
        } catch (IOException e) {
            raise(new DataSavingExceptionEvent(e));
        }
    }

}
