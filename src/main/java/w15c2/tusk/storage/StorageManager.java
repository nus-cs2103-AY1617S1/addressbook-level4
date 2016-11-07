package w15c2.tusk.storage;

import com.google.common.eventbus.Subscribe;

import w15c2.tusk.commons.collections.UniqueItemCollection;
import w15c2.tusk.commons.core.ComponentManager;
import w15c2.tusk.commons.core.LogsCenter;
import w15c2.tusk.commons.events.model.AliasChangedEvent;
import w15c2.tusk.commons.events.model.NewTaskListEvent;
import w15c2.tusk.commons.events.model.TaskManagerChangedEvent;
import w15c2.tusk.commons.events.storage.DataSavingExceptionEvent;
import w15c2.tusk.commons.exceptions.DataConversionException;
import w15c2.tusk.model.Alias;
import w15c2.tusk.model.UserPrefs;
import w15c2.tusk.model.task.Task;
import w15c2.tusk.storage.alias.AliasStorage;
import w15c2.tusk.storage.alias.XmlAliasStorage;
import w15c2.tusk.storage.task.TaskStorage;
import w15c2.tusk.storage.task.XmlTaskManagerStorage;

import java.io.IOException;
import java.util.Optional;
import java.util.logging.Logger;

//@@author A0143107U
/**
 * Manages storage of TaskManager data in local storage.
 */
public class StorageManager extends ComponentManager implements Storage {

    private static final Logger logger = LogsCenter.getLogger(StorageManager.class);
    private TaskStorage taskManagerStorage;
    private AliasStorage aliasStorage;
    private UserPrefsStorage userPrefsStorage;


    public StorageManager(TaskStorage taskManagerStorage, AliasStorage aliasStorage, UserPrefsStorage userPrefsStorage) {
        super();
        this.taskManagerStorage = taskManagerStorage;
        this.aliasStorage = aliasStorage;
        this.userPrefsStorage = userPrefsStorage;
    }

    public StorageManager(String taskManagerFilePath, String aliasFilePath, String userPrefsFilePath) {
        this(new XmlTaskManagerStorage(taskManagerFilePath), new XmlAliasStorage(aliasFilePath), new JsonUserPrefsStorage(userPrefsFilePath));
    }

    // ================ UserPrefs methods ==============================

    /**
     * Reads userPrefs from UserPrefsStorage
     */
    @Override
    public Optional<UserPrefs> readUserPrefs() throws DataConversionException, IOException {
        return userPrefsStorage.readUserPrefs();
    }

    /**
     * Saves userPrefs to UserPrefsStorage
     */
    @Override
    public void saveUserPrefs(UserPrefs userPrefs) throws IOException {
        userPrefsStorage.saveUserPrefs(userPrefs);
    }


    // ================ TaskManager methods ==============================

    @Override
    public String getTaskManagerFilePath() {
        return taskManagerStorage.getTaskManagerFilePath();
    }

    /**
     * Reads task manager from TaskStorage
     */
    @Override
    public Optional<UniqueItemCollection<Task>> readTaskManager() throws DataConversionException, IOException {
        return readTaskManager(taskManagerStorage.getTaskManagerFilePath());
    }
    
    @Override
    public Optional<UniqueItemCollection<Task>> readTaskManager(String filePath) throws DataConversionException, IOException {
        logger.fine("Attempting to read data from file: " + filePath);
        return taskManagerStorage.readTaskManager(filePath);
    }

    /**
     * Saves task manager to TaskStorage
     */
    @Override
    public void saveTaskManager(UniqueItemCollection<Task> taskManager) throws IOException {
        saveTaskManager(taskManager, taskManagerStorage.getTaskManagerFilePath());
    }

    @Override
    public void saveTaskManager(UniqueItemCollection<Task> taskManager, String filePath) throws IOException {
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
    
    @Subscribe
    public void handleNewTaskListEvent(NewTaskListEvent event) {
        logger.info(LogsCenter.getEventHandlingLogMessage(event, "Local data changed, saving to file"));
        try {
            saveTaskManager(event.newTasks);
        } catch (IOException e) {
            raise(new DataSavingExceptionEvent(e));
        }
    }

    
 // ================ Alias methods ==============================

    @Override
    public String getAliasFilePath() {
        return aliasStorage.getAliasFilePath();
    }

    /**
     * Reads alias from AliasStorage
     */
    @Override
    public Optional<UniqueItemCollection<Alias>> readAlias() throws DataConversionException, IOException {
        return readAlias(aliasStorage.getAliasFilePath());
    }

    @Override
    public Optional<UniqueItemCollection<Alias>> readAlias(String filePath) throws DataConversionException, IOException {
        logger.fine("Attempting to read data from file: " + filePath);
        return aliasStorage.readAlias(filePath);
    }

    /**
     * Saves alias to AliasStorage
     */
    @Override
    public void saveAlias(UniqueItemCollection<Alias> alias) throws IOException {
        saveAlias(alias, aliasStorage.getAliasFilePath());
    }

    @Override
    public void saveAlias(UniqueItemCollection<Alias> alias, String filePath) throws IOException {
        logger.fine("Attempting to write to data file: " + filePath);
        aliasStorage.saveAlias(alias, filePath);
    }

    
    @Override
    @Subscribe
    public void handleAliasChangedEvent(AliasChangedEvent event) {
        logger.info(LogsCenter.getEventHandlingLogMessage(event, "Local data changed, saving to file"));
        try {
            saveAlias(event.data);
        } catch (IOException e) {
            raise(new DataSavingExceptionEvent(e));
        }
    }
}
