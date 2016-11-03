package seedu.task.storage;

import com.google.common.eventbus.Subscribe;

import seedu.task.commons.core.ComponentManager;
import seedu.task.commons.core.Config;
import seedu.task.commons.core.LogsCenter;
import seedu.task.commons.events.model.TaskManagerChangedEvent;
import seedu.task.commons.events.storage.ConfigFilePathChangedEvent;
import seedu.task.commons.events.storage.DataSavingExceptionEvent;
import seedu.task.commons.events.storage.FilePathChangedEvent;
import seedu.task.commons.exceptions.DataConversionException;
import seedu.task.commons.util.ConfigUtil;
import seedu.task.model.ReadOnlyTaskManager;
import seedu.task.model.UserPrefs;

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
    private Config config;


    public StorageManager(TaskManagerStorage taskManagerStorage, UserPrefsStorage userPrefsStorage) {
        super();
        this.taskManagerStorage = taskManagerStorage;
        this.userPrefsStorage = userPrefsStorage;
        this.config = new Config();
    }

    public StorageManager(Config config) {
        this(new XmlTaskManagerStorage(config.getTaskManagerFilePath()), new JsonUserPrefsStorage(config.getUserPrefsFilePath()));
        this.config = config;
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
    //@@author A0144939R
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
    public void saveTaskManager(ReadOnlyTaskManager TaskManager) throws IOException {
        saveTaskManager(TaskManager, taskManagerStorage.getTaskManagerFilePath());
    }

    @Override
    public void saveTaskManager(ReadOnlyTaskManager TaskManager, String filePath) throws IOException {
        logger.fine("Attempting to write to data file: " + filePath);
        taskManagerStorage.saveTaskManager(TaskManager, filePath);
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
    //@@author A0144939R
    @Subscribe
    public void handleFilePathChangedEvent(FilePathChangedEvent event) throws DataConversionException {
        //ReadOnlyTaskManager taskManager
        try {
            logger.info(LogsCenter.getEventHandlingLogMessage(event, "File path change requested, updating file path"));
            setTaskManagerFilePath(event.newFilePath);
            saveTaskManager(event.taskManager);
            config.setTaskManagerFilePath(event.newFilePath);
            ConfigUtil.saveConfig(config, Config.DEFAULT_CONFIG_FILE);
            raise(new ConfigFilePathChangedEvent(event.newFilePath));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}
