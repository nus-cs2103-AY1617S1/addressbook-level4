package seedu.tasklist.storage;

import seedu.tasklist.commons.exceptions.DataConversionException;
import seedu.tasklist.model.ReadOnlyTaskList;

import java.io.IOException;
import java.util.Optional;

/**
 * Represents a storage for {@link seedu.tasklist.model.TaskList}.
 */
public interface TaskListStorage {

    /**
     * Returns the file path of the data file.
     */
    String getTaskListFilePath();

    /**
     * Returns TaskList data as a {@link ReadOnlyTaskList}.
     *   Returns {@code Optional.empty()} if storage file is not found.
     * @throws DataConversionException if the data in storage is not in the expected format.
     * @throws IOException if there was any problem when reading from the storage.
     */
    Optional<ReadOnlyTaskList> readTaskList() throws DataConversionException, IOException;

    /**
     * Saves the given {@link ReadOnlyTaskList} to the storage.
     * @param taskList cannot be null.
     * @throws IOException if there was any problem writing to the file.
     */
    void saveTaskList(ReadOnlyTaskList taskList) throws IOException;
}
