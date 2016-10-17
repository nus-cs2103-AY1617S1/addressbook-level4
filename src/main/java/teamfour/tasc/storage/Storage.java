package teamfour.tasc.storage;

import teamfour.tasc.commons.events.model.TaskListChangedEvent;
import teamfour.tasc.commons.events.storage.DataSavingExceptionEvent;
import teamfour.tasc.commons.exceptions.DataConversionException;
import teamfour.tasc.model.ReadOnlyTaskList;
import teamfour.tasc.model.UserPrefs;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Optional;

/**
 * API of the Storage component
 */
public interface Storage extends TaskListStorage, UserPrefsStorage {

    @Override
    Optional<UserPrefs> readUserPrefs() throws DataConversionException, IOException;

    @Override
    void saveUserPrefs(UserPrefs userPrefs) throws IOException;

    @Override
    String getTaskListFilePath();

    @Override
    Optional<ReadOnlyTaskList> readTaskList() throws DataConversionException, FileNotFoundException;

    @Override
    void saveTaskList(ReadOnlyTaskList taskList) throws IOException;

    /**
     * Saves the current version of the Task List to the hard disk.
     *   Creates the data file if it is missing.
     * Raises {@link DataSavingExceptionEvent} if there was an error during saving.
     */
    void handleTaskListChangedEvent(TaskListChangedEvent tlce);

    void changeTaskListStorage(String newTaskListFilePath) throws FileNotFoundException, DataConversionException;
}
