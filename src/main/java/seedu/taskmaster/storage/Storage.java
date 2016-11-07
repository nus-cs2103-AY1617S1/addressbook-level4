package seedu.taskmaster.storage;

import seedu.taskmaster.commons.events.model.FilePathChangeEvent;
import seedu.taskmaster.commons.events.model.TaskListChangedEvent;
import seedu.taskmaster.commons.events.storage.DataSavingExceptionEvent;
import seedu.taskmaster.commons.exceptions.DataConversionException;
import seedu.taskmaster.model.ReadOnlyTaskMaster;
import seedu.taskmaster.model.UserPrefs;

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
    Optional<ReadOnlyTaskMaster> readTaskList() throws DataConversionException, FileNotFoundException;

    @Override
    void saveTaskList(ReadOnlyTaskMaster taskList) throws IOException;

    /**
     * Saves the current version of the Task List to the hard disk.
     *   Creates the data file if it is missing.
     * Raises {@link DataSavingExceptionEvent} if there was an error during saving.
     */
    void handleTaskListChangedEvent(TaskListChangedEvent abce);
    
    /**
     * Changes the file path for current storage manager.
     */
    void handleFilePathChangeEvent(FilePathChangeEvent fpce);
}
