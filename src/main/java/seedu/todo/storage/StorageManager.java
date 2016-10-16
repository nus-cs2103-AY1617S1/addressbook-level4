package seedu.todo.storage;

import java.io.IOException;
import java.util.Optional;
import java.util.logging.Logger;

import seedu.todo.commons.core.ComponentManager;
import seedu.todo.commons.core.LogsCenter;
import seedu.todo.commons.events.storage.DataSavingExceptionEvent;
import seedu.todo.commons.exceptions.DataConversionException;
import seedu.todo.model.ImmutableTodoList;
import seedu.todo.model.UserPrefs;

/**
 * Manages storage of AddressBook data in local storage.
 */
public class StorageManager extends ComponentManager implements Storage {

    private static final Logger logger = LogsCenter.getLogger(StorageManager.class);
    private TodoListStorage todoListStorage;
    private UserPrefsStorage userPrefsStorage;

    public StorageManager(TodoListStorage todoListStorage, UserPrefsStorage userPrefsStorage) {
        super();
        this.todoListStorage = todoListStorage;
        this.userPrefsStorage = userPrefsStorage;
    }

    public StorageManager(String todoListFilePath, String userPrefsFilePath) {
        this(new TodoListStorage(todoListFilePath), new UserPrefsStorage(userPrefsFilePath));
    }

    // ================ UserPrefs methods ==============================

    @Override
    public UserPrefs readUserPrefs() throws DataConversionException, IOException {
        return userPrefsStorage.read();
    }

    @Override
    public void saveUserPrefs(UserPrefs userPrefs) throws IOException {
        userPrefsStorage.save(userPrefs);
    }

    // ================ TodoList methods ==============================
}
