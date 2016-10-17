package seedu.address.storage;

import seedu.address.commons.events.model.TaskSchedulerChangedEvent;
import seedu.address.commons.events.storage.DataSavingExceptionEvent;
import seedu.address.commons.exceptions.DataConversionException;
import seedu.address.model.ReadOnlyTaskScheduler;
import seedu.address.model.UserPrefs;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Optional;

/**
 * API of the Storage component
 */
public interface Storage extends TaskSchedulerStorage, UserPrefsStorage {

    @Override
    Optional<UserPrefs> readUserPrefs() throws DataConversionException, IOException;

    @Override
    void saveUserPrefs(UserPrefs userPrefs) throws IOException;

    @Override
    String getTaskSchedulerFilePath();

    @Override
    Optional<ReadOnlyTaskScheduler> readTaskScheduler() throws DataConversionException, FileNotFoundException;

    @Override
    void saveTaskScheduler(ReadOnlyTaskScheduler taskScheduler) throws IOException;

    /**
     * Saves the current version of the Task Scheduler to the hard disk.
     *   Creates the data file if it is missing.
     * Raises {@link DataSavingExceptionEvent} if there was an error during saving.
     */
    void handleTaskSchedulerChangedEvent(TaskSchedulerChangedEvent abce);
    
    void setTaskSchedulerFilePath(String newPath);
}
