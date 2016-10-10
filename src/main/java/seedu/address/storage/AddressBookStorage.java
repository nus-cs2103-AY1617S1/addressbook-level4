package seedu.address.storage;

import seedu.address.commons.exceptions.DataConversionException;
import seedu.address.model.ReadOnlyLifeKeeper;

import java.io.IOException;
import java.util.Optional;

/**
 * Represents a storage for {@link seedu.address.model.AddressBook}.
 */
public interface AddressBookStorage {

    /**
     * Returns the file path of the data file.
     */
    String getAddressBookFilePath();

    /**
     * Returns AddressBook data as a {@link ReadOnlyLifeKeeper}.
     *   Returns {@code Optional.empty()} if storage file is not found.
     * @throws DataConversionException if the data in storage is not in the expected format.
     * @throws IOException if there was any problem when reading from the storage.
     */
    Optional<ReadOnlyLifeKeeper> readAddressBook() throws DataConversionException, IOException;

    /**
     * @see #getAddressBookFilePath()
     */
    Optional<ReadOnlyLifeKeeper> readAddressBook(String filePath) throws DataConversionException, IOException;

    /**
     * Saves the given {@link ReadOnlyLifeKeeper} to the storage.
     * @param addressBook cannot be null.
     * @throws IOException if there was any problem writing to the file.
     */
    void saveAddressBook(ReadOnlyLifeKeeper addressBook) throws IOException;

    /**
     * @see #saveAddressBook(ReadOnlyLifeKeeper)
     */
    void saveAddressBook(ReadOnlyLifeKeeper addressBook, String filePath) throws IOException;

}
