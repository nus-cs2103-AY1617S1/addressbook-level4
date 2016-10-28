package seedu.address.storage;

import seedu.address.commons.events.model.TaskManagerChangedEvent;
import seedu.address.commons.events.storage.DataSavingExceptionEvent;
import seedu.address.commons.events.storage.RedoStoragePathChangedEvent;
import seedu.address.commons.events.storage.StoragePathChangedBackEvent;
import seedu.address.commons.events.storage.StoragePathChangedEvent;
import seedu.address.commons.exceptions.DataConversionException;
import seedu.address.model.ReadOnlyTaskManager;
import seedu.address.model.UserPrefs;

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
    void setTaskManagerFilePath(String filePath);

    @Override
    Optional<ReadOnlyTaskManager> readTaskManager() throws DataConversionException, IOException;

    @Override
    void saveTaskManager(ReadOnlyTaskManager addressBook) throws IOException;
    
    @Override
    void deleteTaskManager() throws IOException;

    /**
     * Saves the current version of the Task Manager to the hard disk.
     *   Creates the data file if it is missing.
     * Raises {@link DataSavingExceptionEvent} if there was an error during saving.
     */
    void handleTaskManagerChangedEvent(TaskManagerChangedEvent abce);
    
    //@@author A0146123R
    /**
     * Saves the current version of the Task Manager to a new file in hard disk.
     *   Delete the old data file if it is specified.
     * Raises {@link DataSavingExceptionEvent} if there was an error during saving.
     */
    void handleStoragePathChangedEvent(StoragePathChangedEvent abce);
    
    /**
     * Saves the current version of the Task Manager to the previous file in hard disk.
     *   Delete the new data file if it is specified.
     */
    void handleStoragePathChangedBackEvent(StoragePathChangedBackEvent abce);
    
    /**
     * Redo saves the current version of the Task Manager to the new file in hard disk.
     *   Delete the new data file if it was previously specified.
     */
    void handleRedoStoragePathChangedEvent(RedoStoragePathChangedEvent abce);
}
