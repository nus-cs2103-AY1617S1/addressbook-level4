package seedu.taskitty.storage;

import com.google.common.eventbus.Subscribe;

import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Alert.AlertType;
import seedu.taskitty.commons.core.ComponentManager;
import seedu.taskitty.commons.core.LogsCenter;
import seedu.taskitty.commons.events.model.TaskManagerChangedEvent;
import seedu.taskitty.commons.events.storage.DataSavingExceptionEvent;
import seedu.taskitty.commons.exceptions.DataConversionException;
import seedu.taskitty.commons.util.FileUtil;
import seedu.taskitty.model.ReadOnlyTaskManager;
import seedu.taskitty.model.UserPrefs;

import java.io.File;
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
    
    //@@author A0135793W
    /**
     * Sets appropriate filepath in TaskManagerStorage using an internal method ({@link #changeTaskManager(String, Optional)})
     */
    public void setFilePath(String taskManagerFilePath) throws DataConversionException, IOException {
        Optional<ReadOnlyTaskManager> data;
        try {
            data = taskManagerStorage.readTaskManager();
            if (data.isPresent()) {
                changeTaskManager(taskManagerFilePath, data);
            } 
        } catch (DataConversionException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }     
    }
    
    /**
     * Actual method that changes the filepath of TaskManagerStorage
     * @param taskManagerFilePath
     * @param data
     * @throws IOException
     * @throws DataConversionException
     */
    private void changeTaskManager(String taskManagerFilePath, Optional<ReadOnlyTaskManager> data)
            throws IOException, DataConversionException {
        //copy current data into new file path
        taskManagerStorage.saveTaskManager(data.get(),taskManagerFilePath); 
        taskManagerStorage.setFilePath(taskManagerFilePath);
        handleTaskManagerChangedEvent(new TaskManagerChangedEvent(data.get()));
    }
    
    /**
     * Allows users to decide whether or not to overwrite or load an existing file. 
     * There are 2 cases:
     * 1) Filepath input by user exists and there is new data on the current task manager.
     *    In this case, users will be prompted as to whether or not they want to overwrite
     *    the existing file with the new data on the current task manager.
     *    @return false if user chooses not to overwrite the existing file.
     * 2) Filepath input by user exists but there is no data on the current task manager
     *    (task manager is empty). 
     *    @return true for this case. The data on the existing file will be loaded onto the 
     *    task manager
     */
    public boolean toOverwriteOrLoad(String filepath) throws DataConversionException, IOException {
        File file = new File(filepath);
        Optional<ReadOnlyTaskManager> data;
        try {
            data = taskManagerStorage.readTaskManager();
            //current file exists and there is data present in the current task manager
            if (FileUtil.isFileExists(file) && data.isPresent()) {
                Alert alert = new Alert(AlertType.CONFIRMATION, "Overwrite existing file?", ButtonType.YES, ButtonType.NO, ButtonType.CANCEL);
                alert.showAndWait();

                if (alert.getResult() == ButtonType.NO) {
                    return false;
                } 
            }
            return true;
        } catch (DataConversionException e) {
            throw new DataConversionException(e);
        } catch (IOException e) {
            throw new IOException(e);
        }
    }
    
    //@@author

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

}
