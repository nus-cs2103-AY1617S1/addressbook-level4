package seedu.taskman.storage;

import seedu.taskman.commons.exceptions.DataConversionException;
import seedu.taskman.model.ReadOnlyTaskMan;
import seedu.taskman.model.TaskMan;

import java.io.IOException;
import java.util.Optional;

/**
 * Represents a storage for {@link TaskMan}.
 */
public interface TaskManStorage {

    /**
     * Returns the file path of the data file.
     */
    String getTaskManFilePath();

    /**
     * Returns TaskMan data as a {@link ReadOnlyTaskMan}.
     *   Returns {@code Optional.empty()} if storage file is not found.
     * @throws DataConversionException if the data in storage is not in the expected format.
     * @throws IOException if there was any problem when reading from the storage.
     */
    Optional<ReadOnlyTaskMan> readTaskMan() throws DataConversionException, IOException;

    /**
     * @see #getTaskManFilePath()
     */
    Optional<ReadOnlyTaskMan> readTaskMan(String filePath) throws DataConversionException, IOException;

    /**
     * Saves the given {@link ReadOnlyTaskMan} to the storage.
     * @param taskMan cannot be null.
     * @throws IOException if there was any problem writing to the file.
     */
    void saveTaskMan(ReadOnlyTaskMan taskMan) throws IOException;

    /**
     * @see #saveTaskMan(ReadOnlyTaskMan)
     */
    void saveTaskMan(ReadOnlyTaskMan taskMan, String filePath) throws IOException;

}
