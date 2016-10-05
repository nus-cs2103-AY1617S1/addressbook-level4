package seedu.smartscheduler.storage;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Optional;

import seedu.smartscheduler.commons.events.model.TaskListChangedEvent;
import seedu.smartscheduler.commons.events.storage.DataSavingExceptionEvent;
import seedu.smartscheduler.commons.exceptions.DataConversionException;
import seedu.smartscheduler.model.ReadOnlyTaskList;
import seedu.smartscheduler.model.UserPrefs;

/**
 * API of the Storage component
 */
public interface Storage extends TaskListStorage, UserPrefsStorage {

    @Override
    Optional<UserPrefs> readUserPrefs() throws DataConversionException, IOException;

    @Override
    void saveUserPrefs(UserPrefs userPrefs) throws IOException;

    @Override
    String getAddressBookFilePath();

    @Override
    Optional<ReadOnlyTaskList> readAddressBook() throws DataConversionException, FileNotFoundException;

    @Override
    void saveAddressBook(ReadOnlyTaskList addressBook) throws IOException;

    /**
     * Saves the current version of the Address Book to the hard disk.
     *   Creates the data file if it is missing.
     * Raises {@link DataSavingExceptionEvent} if there was an error during saving.
     */
    void handleAddressBookChangedEvent(TaskListChangedEvent abce);
}
