package seedu.todo.storage;

import java.io.IOException;
import java.util.Optional;

import seedu.todo.commons.exceptions.DataConversionException;
import seedu.todo.model.ReadOnlyToDoList;

/**
 * Represents a storage for {@link seedu.todo.model.DoDoBird}.
 */
public interface ToDoListStorage {

    /**
     * Returns the file path of the data file.
     */
    String getToDoListFilePath();
    
    /**
     * Sets the file path of the data file.
     */
    void setToDoListFilePath(String filepath);

    /**
     * Returns ToDoList data as a {@link ReadOnlyToDoList}.
     *   Returns {@code Optional.empty()} if storage file is not found.
     * @throws DataConversionException if the data in storage is not in the expected format.
     * @throws IOException if there was any problem when reading from the storage.
     */
    Optional<ReadOnlyToDoList> readToDoList() throws DataConversionException, IOException;

    /**
     * @see #getToDoListFilePath()
     */
    Optional<ReadOnlyToDoList> readToDoList(String filePath) throws DataConversionException, IOException;

    /**
     * Saves the given {@link ReadOnlyToDoList} to the storage.
     * @param addressBook cannot be null.
     * @throws IOException if there was any problem writing to the file.
     */
    void saveToDoList(ReadOnlyToDoList addressBook) throws IOException;

    /**
     * @see #saveToDoList(ReadOnlyToDoList)
     */
    void saveToDoList(ReadOnlyToDoList addressBook, String filePath) throws IOException;

}
