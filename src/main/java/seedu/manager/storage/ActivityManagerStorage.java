package seedu.manager.storage;

import java.io.IOException;
import java.util.Optional;

import seedu.manager.commons.exceptions.DataConversionException;
import seedu.manager.model.ReadOnlyActivityManager;

/**
 * Represents a storage for {@link seedu.manager.model.ActivityManager}.
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
