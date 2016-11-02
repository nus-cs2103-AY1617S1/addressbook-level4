//@@author A0138431L
package seedu.savvytasker.storage;

import java.io.IOException;
import java.util.Optional;

import seedu.savvytasker.commons.core.Config;
import seedu.savvytasker.commons.exceptions.DataConversionException;

/**
 * Represents a storage for {@link seedu.savvytasker.commons.core.Config}.
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
     * Saves the given {@link seedu.savvytasker.commons.core.Config} to the storage.
     * @param config cannot be null.
     * @throws IOException if there was any problem writing to the file.
     */
    void saveConfigFile(Config config) throws IOException;
    
}