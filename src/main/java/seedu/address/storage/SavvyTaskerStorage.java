package seedu.address.storage;

import seedu.address.commons.exceptions.DataConversionException;
import seedu.address.model.ReadOnlyAddressBook;
import seedu.address.model.ReadOnlySavvyTasker;

import java.io.IOException;
import java.util.Optional;

/**
 * Represents a storage for {@link seedu.address.model.SavvyTasker}.
 */
public interface SavvyTaskerStorage {

    /**
     * Returns the file path of the data file.
     */
    String getSavvyTaskerFilePath();

    /**
     * Returns SavvyTasker data as a {@link ReadOnlySavvyTasker}.
     *   Returns {@code Optional.empty()} if storage file is not found.
     * @throws DataConversionException if the data in storage is not in the expected format.
     * @throws IOException if there was any problem when reading from the storage.
     */
    Optional<ReadOnlySavvyTasker> readSavvyTasker() throws DataConversionException, IOException;

    /**
     * @see #getSavvyTaskerFilePath()
     */
    Optional<ReadOnlySavvyTasker> readSavvyTasker(String filePath) throws DataConversionException, IOException;

    /**
     * Saves the given {@link ReadOnlySavvyTasker} to the storage.
     * @param savvyTasker cannot be null.
     * @throws IOException if there was any problem writing to the file.
     */
    void saveSavvyTasker(ReadOnlySavvyTasker savvyTasker) throws IOException;

    /**
     * @see #saveSavvyTasker(ReadOnlySavvyTasker)
     */
    void saveSavvyTasker(ReadOnlySavvyTasker savvyTasker, String filePath) throws IOException;

}
