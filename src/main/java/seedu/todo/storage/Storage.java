package seedu.todo.storage;

import java.io.IOException;
import java.util.Optional;

import seedu.todo.commons.exceptions.DataConversionException;
import seedu.todo.model.ImmutableTodoList;
import seedu.todo.model.UserPrefs;

/**
 * API of the Storage component
 */
public interface Storage extends TodoListStorage {

    Optional<UserPrefs> readUserPrefs() throws DataConversionException, IOException;

    void saveUserPrefs(UserPrefs userPrefs) throws IOException;

    @Override
    String getTodoListFilePath();

    @Override
    Optional<ImmutableTodoList> readTodoList();

    @Override
    Optional<ImmutableTodoList> readTodoList(String filePath);

    @Override
    void saveTodoList(ImmutableTodoList todoList);

    @Override
    void saveTodoList(ImmutableTodoList todoList, String filePath);

}
