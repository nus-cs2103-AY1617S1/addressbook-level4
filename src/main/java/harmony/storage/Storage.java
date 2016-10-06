package harmony.storage;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Optional;

import harmony.commons.events.model.TaskManagerChangedEvent;
import harmony.commons.events.storage.DataSavingExceptionEvent;
import harmony.commons.exceptions.DataConversionException;
import harmony.model.ReadOnlyTaskManager;
import harmony.model.UserPrefs;

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
    Optional<ReadOnlyTaskManager> readTaskManager() throws DataConversionException, FileNotFoundException;

    @Override
    void saveTaskManager(ReadOnlyTaskManager taskManager) throws IOException;

    /**
     * Saves the current version of the Schema to the hard disk.
     *   Creates the data file if it is missing.
     * Raises {@link DataSavingExceptionEvent} if there was an error during saving.
     */
    void handleTaskManagerChangedEvent(TaskManagerChangedEvent abce);
}
