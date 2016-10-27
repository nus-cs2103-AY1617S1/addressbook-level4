package seedu.unburden.storage;

import com.google.common.eventbus.Subscribe;

import seedu.unburden.commons.core.ComponentManager;
import seedu.unburden.commons.core.LogsCenter;
import seedu.unburden.commons.events.model.ListOfTaskChangedEvent;
import seedu.unburden.commons.events.storage.*;
import seedu.unburden.commons.exceptions.DataConversionException;
import seedu.unburden.model.ReadOnlyListOfTask;
import seedu.unburden.model.ListOfTask;
import seedu.unburden.model.UserPrefs;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Optional;
import java.util.logging.Logger;

/**
 * Manages storage of ListOfTask data in local storage.
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


    // ================ ListOfTask methods ==============================

    @Override
    public String getTaskListFilePath() {
        return taskListStorage.getTaskListFilePath();
    }

    @Override
    public Optional<ReadOnlyListOfTask> readTaskList() throws DataConversionException, IOException {
        return readTaskList(taskListStorage.getTaskListFilePath());
    }

    @Override
    public Optional<ReadOnlyListOfTask> readTaskList(String filePath) throws DataConversionException, IOException {
        logger.fine("Attempting to read data from file: " + filePath);
        return taskListStorage.readTaskList(filePath);
    }

    @Override
    public void saveTaskList(ReadOnlyListOfTask addressBook) throws IOException {
        saveTaskList(addressBook, taskListStorage.getTaskListFilePath());
    }

    @Override
    public void saveTaskList(ReadOnlyListOfTask addressBook, String filePath) throws IOException {
        logger.fine("Attempting to write to data file: " + filePath);
        taskListStorage.saveTaskList(addressBook, filePath);
    }


    @Override
    @Subscribe
    public void handleListOfTaskChangedEvent(ListOfTaskChangedEvent event) {
        logger.info(LogsCenter.getEventHandlingLogMessage(event, "Local data changed, saving to file"));
        try {
            saveTaskList(event.data);
        } catch (IOException e) {
            raise(new DataSavingExceptionEvent(e));
        }
    }
    
    
    //@@author A0139714B
    @Override
    @Subscribe
    public void handleStoragePathChangeEvent(StoragePathChangedEvent event) {
    	logger.info(LogsCenter.getEventHandlingLogMessage(event, "Storage path changed, updating"));
    	try {
    		ReadOnlyListOfTask oldTaskList = readTaskList(event.oldStoragePath).orElse(new ListOfTask());
    		((XmlTaskListStorage)taskListStorage).setTaskListFilePath(event.newStoragePath);
    		saveTaskList(oldTaskList);
    	} catch (IOException | DataConversionException ee) {
    		raise(new DataSavingExceptionEvent(ee));
    	}
    }

}
