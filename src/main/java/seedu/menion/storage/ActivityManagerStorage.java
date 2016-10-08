package seedu.menion.storage;

import seedu.menion.commons.exceptions.DataConversionException;
import seedu.menion.model.ReadOnlyActivityManager;

import java.io.IOException;
import java.util.Optional;

/**
 * Represents a storage for {@link seedu.menion.model.ActivityManager}.
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
     * @param activityManager cannot be null.
     * @throws IOException if there was any problem writing to the file.
     */
    void saveActivityManager(ReadOnlyActivityManager activityManager) throws IOException;

}
