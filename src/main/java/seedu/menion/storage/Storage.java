package seedu.menion.storage;

import seedu.menion.commons.events.model.TaskManagerChangedEvent;
import seedu.menion.commons.events.storage.DataSavingExceptionEvent;
import seedu.menion.commons.exceptions.DataConversionException;
import seedu.menion.model.ReadOnlyTaskManager;
import seedu.menion.model.UserPrefs;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Optional;

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
    void saveTaskManager(ReadOnlyTaskManager addressBook) throws IOException;

    /**
     * Saves the current version of the Task Manager to the hard disk.
     *   Creates the data file if it is missing.
     * Raises {@link DataSavingExceptionEvent} if there was an error during saving.
     */
    void handleTaskManagerChangedEvent(TaskManagerChangedEvent abce);
}
