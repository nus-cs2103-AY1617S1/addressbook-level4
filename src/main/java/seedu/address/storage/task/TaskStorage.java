package seedu.address.storage.task;

import seedu.address.commons.collections.UniqueItemCollection;
import seedu.address.commons.events.model.AddressBookChangedEvent;
import seedu.address.commons.events.model.TaskManagerChangedEvent;
import seedu.address.commons.events.storage.DataSavingExceptionEvent;
import seedu.address.commons.exceptions.DataConversionException;
import seedu.address.model.ReadOnlyAddressBook;
import seedu.address.model.UserPrefs;
import seedu.address.model.task.Task;
import seedu.address.storage.*;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Optional;

/**
 * API of the TaskStorage component
 */
public interface TaskStorage extends TaskManagerStorage, UserPrefsStorage {

    @Override
    Optional<UserPrefs> readUserPrefs() throws DataConversionException, IOException;

    @Override
    void saveUserPrefs(UserPrefs userPrefs) throws IOException;

    @Override
    String getTaskManagerFilePath();

    @Override
    Optional<UniqueItemCollection<Task>> readTaskManager() throws DataConversionException, IOException;

    @Override
    void saveTaskManager(UniqueItemCollection<Task> taskManager) throws IOException;
    
    /**
     * Saves the current version of the Task Manager to the hard disk.
     *   Creates the data file if it is missing.
     * Raises {@link DataSavingExceptionEvent} if there was an error during saving.
     */
    void handleTaskManagerChangedEvent(TaskManagerChangedEvent tmce);

}
