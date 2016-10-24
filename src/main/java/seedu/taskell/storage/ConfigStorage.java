/** @@author A0142130A-reused**/
package seedu.taskell.storage;

import java.io.IOException;
import java.util.Optional;

import seedu.taskell.commons.core.Config;
import seedu.taskell.commons.exceptions.DataConversionException;

/**
 * Represents a storage for {@link seedu.taskell.commons.core.Config}.
 */
public interface ConfigStorage {
    /**
     * Returns Config data from storage.
     *   Returns {@code Optional.empty()} if storage file is not found.
     * @throws DataConversionException if the data in storage is not in the expected format.
     * @throws IOException if there was any problem when reading from the storage.
     */
    Optional<Config> readConfigFile() throws DataConversionException, IOException;

    /**
     * Saves the given {@link seedu.taskell.commons.core.Config} to the storage.
     * @param config cannot be null.
     * @throws IOException if there was any problem writing to the file.
     */
    void saveConfigFile(Config config) throws IOException;
}
