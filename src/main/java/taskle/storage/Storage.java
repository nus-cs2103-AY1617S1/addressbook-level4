package taskle.storage;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Optional;

import taskle.commons.events.model.TaskManagerChangedEvent;
import taskle.commons.events.storage.DataSavingExceptionEvent;
import taskle.commons.exceptions.DataConversionException;
import taskle.model.ReadOnlyTaskManager;
import taskle.model.UserPrefs;

/**
 * API of the Storage component
 */
public interface Storage extends TaskManagerStorage, UserPrefsStorage {

    @Override
    Optional<UserPrefs> readUserPrefs() throws DataConversionException, IOException;

    @Override
    void saveUserPrefs(UserPrefs userPrefs) throws IOException;

    @Override
    String getTaskManagerFilePath();

    @Override
    Optional<ReadOnlyTaskManager> readTaskManager() throws DataConversionException, IOException;

    @Override
    void saveTaskManager(ReadOnlyTaskManager taskManager) throws IOException;

    /**
     * Saves the current version of the Task Manager to the hard disk.
     *   Creates the data file if it is missing.
     * Raises {@link DataSavingExceptionEvent} if there was an error during saving.
     */
    void handleTaskManagerChangedEvent(TaskManagerChangedEvent abce);
}
