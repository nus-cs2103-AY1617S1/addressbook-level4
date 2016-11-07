package seedu.address.storage;

import seedu.address.commons.exceptions.DataConversionException;
import seedu.address.model.ReadOnlyAliasManager;
import seedu.address.model.ReadOnlyTaskManager;

import java.io.IOException;
import java.util.Optional;

//@@author A0143756Y-reused
/**
 * Represents a storage for {@link seedu.address.model.AliasManager}.
 */
public interface AliasManagerStorage {

    /**
     * Returns the file path of the data file.
     */
    String getAliasManagerFilePath();

    /**
     * Returns AliasManager data as a {@link ReadOnlyAliasManager}.
     *   Returns {@code Optional.empty()} if storage file is not found.
     * @throws DataConversionException if the data in storage is not in the expected format.
     * @throws IOException if there was any problem when reading from the storage.
     */
    Optional<ReadOnlyAliasManager> readAliasManager() throws DataConversionException, IOException;

    /**
     * @see #getAliasManagerFilePath()
     */
    Optional<ReadOnlyAliasManager> readAliasManager(String filePath) throws DataConversionException, IOException;

    /**
     * Saves the given {@link ReadOnlyAliasManager} to the storage.
     * @param aliasManager cannot be null.
     * @throws IOException if there was any problem writing to the file.
     */
    void saveAliasManager(ReadOnlyAliasManager alias) throws IOException;

    /**
     * @see #saveAliasManager(ReadOnlyAliasManager)
     */
    void saveAliasManager(ReadOnlyAliasManager alias, String filePath) throws IOException;

}
