package seedu.unburden.storage;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Optional;

import seedu.unburden.commons.events.model.AddressBookChangedEvent;
import seedu.unburden.commons.events.storage.DataSavingExceptionEvent;
import seedu.unburden.commons.exceptions.DataConversionException;
import seedu.unburden.model.ReadOnlyAddressBook;
import seedu.unburden.model.UserPrefs;

/**
 * API of the Storage component
 */
public interface Storage extends TaskListStorage, UserPrefsStorage {

    @Override
    Optional<UserPrefs> readUserPrefs() throws DataConversionException, IOException;

    @Override
    void saveUserPrefs(UserPrefs userPrefs) throws IOException;

    @Override
    String getTaskListFilePath();

    @Override
    Optional<ReadOnlyAddressBook> readTaskList() throws DataConversionException, IOException;

    @Override
    void saveTaskList(ReadOnlyAddressBook addressBook) throws IOException;

    /**
     * Saves the current version of the Address Book to the hard disk.
     *   Creates the data file if it is missing.
     * Raises {@link DataSavingExceptionEvent} if there was an error during saving.
     */
    void handleAddressBookChangedEvent(AddressBookChangedEvent abce);
}
