package jym.manager.storage;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Optional;

import jym.manager.commons.events.model.TaskManagerChangedEvent;
import jym.manager.commons.events.storage.DataSavingExceptionEvent;
import jym.manager.commons.exceptions.DataConversionException;
import jym.manager.model.ReadOnlyTaskManager;
import jym.manager.model.UserPrefs;

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
