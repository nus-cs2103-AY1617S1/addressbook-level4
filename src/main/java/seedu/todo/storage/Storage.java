package seedu.todo.storage;

import java.io.IOException;
import java.util.Optional;

import seedu.todo.commons.exceptions.DataConversionException;
import seedu.todo.model.ImmutableTodoList;
import seedu.todo.model.UserPrefs;

/**
 * API of the Storage component
 */
public interface Storage {

    Optional<UserPrefs> readUserPrefs() throws DataConversionException, IOException;

    void saveUserPrefs(UserPrefs userPrefs) throws IOException;

    String getTodoListFilePath();

    Optional<ImmutableTodoList> readTodoList();
    
    Optional<ImmutableTodoList> readTodoList(String filePath);

    void saveTodoList(ImmutableTodoList todoList);

    void saveTodoList(ImmutableTodoList todoList, String newLocation);
}
