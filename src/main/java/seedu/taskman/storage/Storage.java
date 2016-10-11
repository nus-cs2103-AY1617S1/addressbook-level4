package seedu.taskman.storage;

import seedu.taskman.commons.events.model.TaskManChangedEvent;
import seedu.taskman.commons.events.storage.DataSavingExceptionEvent;
import seedu.taskman.commons.exceptions.DataConversionException;
import seedu.taskman.model.ReadOnlyTaskMan;
import seedu.taskman.model.UserPrefs;

import java.io.IOException;
import java.util.Optional;

/**
 * API of the Storage component
 */
public interface Storage extends TaskManStorage, UserPrefsStorage {

    @Override
    Optional<UserPrefs> readUserPrefs() throws DataConversionException, IOException;

    @Override
    void saveUserPrefs(UserPrefs userPrefs) throws IOException;

    @Override
    String getTaskManFilePath();

    @Override
    Optional<ReadOnlyTaskMan> readTaskMan() throws DataConversionException, IOException;

    @Override
    void saveTaskMan(ReadOnlyTaskMan taskMan) throws IOException;

    /**
     * Saves the current version of the Task man to the hard disk.
     *   Creates the data file if it is missing.
     * Raises {@link DataSavingExceptionEvent} if there was an error during saving.
     */
    void handleTaskManChangedEvent(TaskManChangedEvent abce);
}
