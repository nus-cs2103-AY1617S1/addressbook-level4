package w15c2.tusk.storage.task;

import java.io.IOException;
import java.util.Optional;

import w15c2.tusk.commons.collections.UniqueItemCollection;
import w15c2.tusk.commons.exceptions.DataConversionException;
import w15c2.tusk.model.task.Task;

/**
 * Represents a storage for {@link w15c2.tusk.model.task.TaskManager}.
 */
public interface TaskManagerStorage {

    /**
     * Returns the file path of the data file.
     */
    String getTaskManagerFilePath();

    /**
     * Returns Task Manager data as a {@link UniqueItemCollection<Task>}.
     *   Returns {@code Optional.empty()} if storage file is not found.
     * @throws DataConversionException if the data in storage is not in the expected format.
     * @throws IOException if there was any problem when reading from the storage.
     */
    Optional<UniqueItemCollection<Task>> readTaskManager() throws DataConversionException, IOException;

    /**
     * @see #getTaskManagerFilePath()
     */
    Optional<UniqueItemCollection<Task>> readTaskManager(String filePath) throws DataConversionException, IOException;

    /**
     * Saves the given {@link UniqueItemCollection<Task>} to the storage.
     * @param taskManager cannot be null.
     * @throws IOException if there was any problem writing to the file.
     */
    void saveTaskManager(UniqueItemCollection<Task> taskManager) throws IOException;

    /**
     * @see #saveTaskManager(UniqueItemCollection<Task>)
     */
    void saveTaskManager(UniqueItemCollection<Task> taskManager, String filePath) throws IOException;

}
