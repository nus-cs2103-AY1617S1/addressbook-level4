package seedu.lifekeeper.storage;

import seedu.lifekeeper.commons.exceptions.DataConversionException;
import seedu.lifekeeper.model.ReadOnlyLifeKeeper;

import java.io.IOException;
import java.util.Optional;

/**
 * Represents a storage for {@link seedu.lifekeeper.model.LifeKeeper}.
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
     * @param lifeKeeper cannot be null.
     * @throws IOException if there was any problem writing to the file.
     */
    void saveAddressBook(ReadOnlyLifeKeeper lifeKeeper) throws IOException;

    /**
     * @see #saveAddressBook(ReadOnlyLifeKeeper)
     */
    void saveAddressBook(ReadOnlyLifeKeeper lifeKeeper, String filePath) throws IOException;

}
