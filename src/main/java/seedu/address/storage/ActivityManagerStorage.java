package seedu.address.storage;

import seedu.address.commons.exceptions.DataConversionException;
import seedu.address.model.ReadOnlyActivityManager;

import java.io.IOException;
import java.util.Optional;

/**
 * Represents a storage for {@link seedu.address.model.ActivityManager}.
 */
public interface ActivityManagerStorage {

    /**
     * Returns the file path of the data file.
     */
    String getActivityManagerFilePath();

    /**
     * Returns ActivityManager data as a {@link ReadOnlyActivityManager}.
     *   Returns {@code Optional.empty()} if storage file is not found.
     * @throws DataConversionException if the data in storage is not in the expected format.
     * @throws IOException if there was any problem when reading from the storage.
     */
    Optional<ReadOnlyActivityManager> readActivityManager() throws DataConversionException, IOException;

    /**
     * Saves the given {@link ReadOnlyActivityManager} to the storage.
     * @param addressBook cannot be null.
     * @throws IOException if there was any problem writing to the file.
     */
    void saveActivityManager(ReadOnlyActivityManager addressBook) throws IOException;

}
