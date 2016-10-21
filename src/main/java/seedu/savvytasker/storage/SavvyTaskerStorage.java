package seedu.savvytasker.storage;

import java.io.IOException;
import java.util.Optional;

import seedu.savvytasker.commons.exceptions.DataConversionException;
import seedu.savvytasker.model.ReadOnlySavvyTasker;

/**
 * Represents a storage for {@link seedu.savvytasker.model.SavvyTasker}.
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
