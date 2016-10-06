package seedu.todo.storage;

import java.io.IOException;
import java.util.Optional;

import seedu.todo.commons.events.model.AddressBookChangedEvent;
import seedu.todo.commons.events.storage.DataSavingExceptionEvent;
import seedu.todo.commons.exceptions.DataConversionException;
import seedu.todo.model.ReadOnlyAddressBook;
import seedu.todo.model.ImmutableTodoList;
import seedu.todo.model.UserPrefs;

/**
 * API of the Storage component
 */
public interface Storage extends TodoListStorage, UserPrefsStorage {

    @Override
    Optional<UserPrefs> readUserPrefs() throws DataConversionException, IOException;

    @Override
    void saveUserPrefs(UserPrefs userPrefs) throws IOException;

    @Override
    String getTodoListFilePath();

    @Override
    Optional<ImmutableTodoList> readTodoList() throws DataConversionException, IOException;

    @Override
    Optional<ImmutableTodoList> readTodoList(String filePath) throws DataConversionException, IOException;

    @Override
    void saveTodoList(ImmutableTodoList todoList) throws IOException;

    @Override
    void saveTodoList(ImmutableTodoList todoList, String filePath) throws IOException;

}
