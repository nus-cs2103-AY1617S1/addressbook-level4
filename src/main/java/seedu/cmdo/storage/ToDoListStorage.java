package seedu.cmdo.storage;

import seedu.cmdo.commons.exceptions.DataConversionException;
import seedu.cmdo.model.ReadOnlyToDoList;

import java.io.IOException;
import java.util.Optional;

/**
 * Represents a storage for {@link seedu.cmdo.model.ToDoList}.
 */
public interface ToDoListStorage {

    /**
     * Returns the file path of the data file.
     */
    String getToDoListFilePath();

    /**
     * Returns ToDoList data as a {@link ReadOnlyToDoList}.
     *   Returns {@code Optional.empty()} if storage file is not found.
     * @throws DataConversionException if the data in storage is not in the expected format.
     * @throws IOException if there was any problem when reading from the storage.
     */
    Optional<ReadOnlyToDoList> readToDoList() throws DataConversionException, IOException;

    /**
     * Saves the given {@link ReadOnlyToDoList} to the storage.
     * @param addressBook cannot be null.
     * @throws IOException if there was any problem writing to the file.
     */
    void saveToDoList(ReadOnlyToDoList toDoList) throws IOException;

}
