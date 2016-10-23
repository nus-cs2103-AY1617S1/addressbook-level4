package seedu.task.storage;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Optional;

import seedu.task.commons.events.model.TaskManagerChangedEvent;
import seedu.task.commons.events.storage.DataSavingExceptionEvent;
import seedu.task.commons.exceptions.DataConversionException;
import seedu.task.model.ReadOnlyTaskManager;
import seedu.task.model.UserPrefs;

/**
 * API of the Storage component
 */
public interface Storage extends TaskManagerStorage, UserPrefsStorage {

    @Override
    Optional<UserPrefs> readUserPrefs() throws DataConversionException, IOException, FileNotFoundException;

    @Override
    void saveUserPrefs(UserPrefs userPrefs) throws IOException, FileNotFoundException;

    @Override
    String getAddressBookFilePath();

    @Override
    Optional<ReadOnlyTaskManager> readAddressBook() throws DataConversionException, IOException, FileNotFoundException;

    @Override
    void saveAddressBook(ReadOnlyTaskManager addressBook) throws IOException, FileNotFoundException;

    /**
     * Saves the current version of the Address Book to the hard disk.
     *   Creates the data file if it is missing.
     * Raises {@link DataSavingExceptionEvent} if there was an error during saving.
     */
    void handleAddressBookChangedEvent(TaskManagerChangedEvent abce);
}
