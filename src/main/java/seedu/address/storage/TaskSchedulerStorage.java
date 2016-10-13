package seedu.address.storage;

import seedu.address.commons.exceptions.DataConversionException;
import seedu.address.model.ReadOnlyTaskScheduler;

import java.io.IOException;
import java.util.Optional;

/**
 * Represents a storage for {@link seedu.address.model.TaskScheduler}.
 */
public interface TaskSchedulerStorage {

    /**
     * Returns the file path of the data file.
     */
    String getTaskSchedulerFilePath();

    /**
     * Returns TaskScheduler data as a {@link ReadOnlyTaskScheduler}.
     *   Returns {@code Optional.empty()} if storage file is not found.
     * @throws DataConversionException if the data in storage is not in the expected format.
     * @throws IOException if there was any problem when reading from the storage.
     */
    Optional<ReadOnlyTaskScheduler> readTaskScheduler() throws DataConversionException, IOException;

    /**
     * Saves the given {@link ReadOnlyTaskScheduler} to the storage.
     * @param taskScheduler cannot be null.
     * @throws IOException if there was any problem writing to the file.
     */
    void saveTaskScheduler(ReadOnlyTaskScheduler taskScheduler) throws IOException;

}
