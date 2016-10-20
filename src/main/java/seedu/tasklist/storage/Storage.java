package seedu.tasklist.storage;

import seedu.tasklist.commons.events.model.TaskListChangedEvent;
import seedu.tasklist.commons.events.storage.DataSavingExceptionEvent;
import seedu.tasklist.commons.exceptions.DataConversionException;
import seedu.tasklist.model.ReadOnlyTaskList;
import seedu.tasklist.model.UserPrefs;

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
     * Saves the current version of the task list to the hard disk.
     *   Creates the data file if it is missing.
     * Raises {@link DataSavingExceptionEvent} if there was an error during saving.
     */
    void handleTaskListEvent(TaskListChangedEvent taskListChangedEvent);
    
    void setTaskListFilePath(String filepath);
}
