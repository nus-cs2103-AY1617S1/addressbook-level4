package seedu.address.storage;

import seedu.address.commons.exceptions.DataConversionException;
import seedu.address.model.AliasManager;
import seedu.address.model.ReadOnlyTaskManager;

import java.io.IOException;
import java.util.Optional;

/**
 * Represents a storage for {@link seedu.address.model.TaskManager}.
 */
public interface AliasManagerStorage {

    /**
     * Returns the file path of the data file.
     */
    String getAliasFilePath();

    /**
     * Returns TaskManager data as a {@link ReadOnlyTaskManager}.
     *   Returns {@code Optional.empty()} if storage file is not found.
     * @throws DataConversionException if the data in storage is not in the expected format.
     * @throws IOException if there was any problem when reading from the storage.
     */
    Optional<AliasManager> readAlias() throws DataConversionException, IOException;

    /**
     * @see #getTaskManagerFilePath()
     */
    Optional<AliasManager> readAlias(String filePath) throws DataConversionException, IOException;

    /**
     * Saves the given {@link ReadOnlyTaskManager} to the storage.
     * @param addressBook cannot be null.
     * @throws IOException if there was any problem writing to the file.
     */
    void saveAlias(AliasManager alias) throws IOException;

    /**
     * @see #saveTaskManager(ReadOnlyTaskManager)
     */
    void saveAlias(AliasManager alias, String filePath) throws IOException;

}
