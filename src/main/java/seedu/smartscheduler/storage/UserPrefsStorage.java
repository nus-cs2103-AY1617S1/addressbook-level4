package seedu.smartscheduler.storage;

import java.io.IOException;
import java.util.Optional;

import seedu.smartscheduler.commons.exceptions.DataConversionException;
import seedu.smartscheduler.model.UserPrefs;

/**
 * Represents a storage for {@link seedu.smartscheduler.model.UserPrefs}.
 */
public interface UserPrefsStorage {

    /**
     * Returns UserPrefs data from storage.
     *   Returns {@code Optional.empty()} if storage file is not found.
     * @throws DataConversionException if the data in storage is not in the expected format.
     * @throws IOException if there was any problem when reading from the storage.
     */
    Optional<UserPrefs> readUserPrefs() throws DataConversionException, IOException;

    /**
     * Saves the given {@link seedu.smartscheduler.model.UserPrefs} to the storage.
     * @param userPrefs cannot be null.
     * @throws IOException if there was any problem writing to the file.
     */
    void saveUserPrefs(UserPrefs userPrefs) throws IOException;

}
