package seedu.taskscheduler.storage;

import seedu.taskscheduler.commons.events.model.TaskSchedulerChangedEvent;
import seedu.taskscheduler.commons.events.storage.DataSavingExceptionEvent;
import seedu.taskscheduler.commons.exceptions.DataConversionException;
import seedu.taskscheduler.model.ReadOnlyTaskScheduler;
import seedu.taskscheduler.model.UserPrefs;

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
