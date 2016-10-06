package seedu.todo.storage;

import java.io.IOException;
import java.util.Optional;

import seedu.todo.commons.exceptions.DataConversionException;
import seedu.todo.model.ImmutableTodoList;

/**
 * Represents a storage for {@link seedu.todo.model.TodoList}.
 */
public interface TodoListStorage {

    /**
     * Returns the file path of the data file.
     */
    String getTodoListFilePath();

    /**
     * Returns TodoList data as a {@link ImmutableTodoList}.
     * Returns {@code Optional.empty()} if storage file is not found.
     * 
     * @throws DataConversionException if the data in storage is not in the expected format.
     * @throws IOException if there was any problem when reading from the storage.
     */
    Optional<ImmutableTodoList> readTodoList() throws DataConversionException, IOException;

    /**
     * @see #getTodoListFilePath()
     */
    Optional<ImmutableTodoList> readTodoList(String filePath) throws DataConversionException, IOException;

    /**
     * Saves the given {@link ImmutableTodoList} to the storage.
     * 
     * @param todoList cannot be null.
     * @throws IOException if there was any problem writing to the file.
     */
    void saveTodoList(ImmutableTodoList todoList) throws IOException;

    /**
     * @see #saveTodoList(ImmutableTodoList)
     */
    void saveTodoList(ImmutableTodoList todoList, String filePath) throws IOException;

}
