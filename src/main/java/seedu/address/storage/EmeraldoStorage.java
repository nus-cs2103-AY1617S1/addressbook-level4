package seedu.address.storage;

import seedu.address.commons.exceptions.DataConversionException;
import seedu.address.model.ReadOnlyEmeraldo;

import java.io.IOException;
import java.util.Optional;

/**
 * Represents a storage for {@link seedu.address.model.AddressBook}.
 */
public interface EmeraldoStorage {

    /**
     * Returns the file path of the data file.
     */
    String getEmeraldoFilePath();

    /**
     * Returns Emeraldo data as a {@link ReadOnlyEmeraldo}.
     *   Returns {@code Optional.empty()} if storage file is not found.
     * @throws DataConversionException if the data in storage is not in the expected format.
     * @throws IOException if there was any problem when reading from the storage.
     */
    Optional<ReadOnlyEmeraldo> readEmeraldo() throws DataConversionException, IOException;

    /**
     * @see #getEmeraldoFilePath()
     */
    Optional<ReadOnlyEmeraldo> readEmeraldo(String filePath) throws DataConversionException, IOException;

    /**
     * Saves the given {@link ReadOnlyEmeraldo} to the storage.
     * @param addressBook cannot be null.
     * @throws IOException if there was any problem writing to the file.
     */
    void saveAddressBook(ReadOnlyEmeraldo addressBook) throws IOException;

    /**
     * @see #saveAddressBook(ReadOnlyEmeraldo)
     */
    void saveAddressBook(ReadOnlyEmeraldo addressBook, String filePath) throws IOException;

}
