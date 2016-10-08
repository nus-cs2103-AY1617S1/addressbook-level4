package seedu.unburden.storage;

import java.io.IOException;
import java.util.Optional;

import seedu.unburden.commons.exceptions.DataConversionException;
import seedu.unburden.model.ReadOnlyListOfTask;

/**
 * Represents a storage for {@link seedu.unburden.model.ListOfTask}.
 */
public interface TaskListStorage {

    /**
     * Returns the file path of the data file.
     */
    String getTaskListFilePath();

    /**
     * Returns ListOfTask data as a {@link ReadOnlyListOfTask}.
     *   Returns {@code Optional.empty()} if storage file is not found.
     * @throws DataConversionException if the data in storage is not in the expected format.
     * @throws IOException if there was any problem when reading from the storage.
     */
    Optional<ReadOnlyListOfTask> readTaskList() throws DataConversionException, IOException;

    /**
     * @see #getTaskListFilePath()
     */
    Optional<ReadOnlyListOfTask> readTaskList(String filePath) throws DataConversionException, IOException;

    /**
     * Saves the given {@link ReadOnlyListOfTask} to the storage.
     * @param addressBook cannot be null.
     * @throws IOException if there was any problem writing to the file.
     */
    void saveTaskList(ReadOnlyListOfTask addressBook) throws IOException;

    /**
     * @see #saveAddressBook(ReadOnlyListOfTask)
     */
    void saveTaskList(ReadOnlyListOfTask addressBook, String filePath) throws IOException;

}
