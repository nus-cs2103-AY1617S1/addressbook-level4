package jym.manager.storage;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Optional;

import jym.manager.commons.events.model.AddressBookChangedEvent;
import jym.manager.commons.events.storage.DataSavingExceptionEvent;
import jym.manager.commons.exceptions.DataConversionException;
import jym.manager.model.ReadOnlyAddressBook;
import jym.manager.model.UserPrefs;

/**
 * API of the Storage component
 */
public interface Storage extends AddressBookStorage, UserPrefsStorage {

    @Override
    Optional<UserPrefs> readUserPrefs() throws DataConversionException, IOException;

    @Override
    void saveUserPrefs(UserPrefs userPrefs) throws IOException;

    @Override
    String getAddressBookFilePath();

    @Override
    Optional<ReadOnlyAddressBook> readAddressBook() throws DataConversionException, IOException;

    @Override
    void saveAddressBook(ReadOnlyAddressBook addressBook) throws IOException;

    /**
     * Saves the current version of the Address Book to the hard disk.
     *   Creates the data file if it is missing.
     * Raises {@link DataSavingExceptionEvent} if there was an error during saving.
     */
    void handleAddressBookChangedEvent(AddressBookChangedEvent abce);
}
