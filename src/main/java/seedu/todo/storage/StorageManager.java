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
        this(new XmlTodoListStorage(todoListFilePath), new UserPrefsStorage(userPrefsFilePath));
    }

    // ================ UserPrefs methods ==============================

    @Override
    public Optional<UserPrefs> readUserPrefs() throws DataConversionException, IOException {
        return userPrefsStorage.read();
    }

    @Override
    public void saveUserPrefs(UserPrefs userPrefs) throws IOException {
        userPrefsStorage.save(userPrefs);
    }

    // ================ TodoList methods ==============================

    @Override
    public String getTodoListFilePath() {
        return todoListStorage.getTodoListFilePath();
    }

    @Override
    public Optional<ImmutableTodoList> readTodoList() {
        return readTodoList(todoListStorage.getTodoListFilePath());
    }

    @Override
    public Optional<ImmutableTodoList> readTodoList(String filePath) {
        logger.fine("Attempting to read data from file: " + filePath);
        
        Optional<ImmutableTodoList> todoListOptional = Optional.empty();
        
        try {
            todoListOptional = todoListStorage.readTodoList();
        } catch (DataConversionException e) {
            logger.warning("Data file not in the correct format. Will be starting with an empty TodoList");
        } catch (IOException e) {
            logger.warning("Problem while reading from the file. Will be starting with an empty TodoList");
        }
        
        return todoListOptional;
    }

    @Override
    public void saveTodoList(ImmutableTodoList todoList) {
        try {
            todoListStorage.saveTodoList(todoList);
        } catch (IOException e) {
            raise(new DataSavingExceptionEvent(e));
        }
    }

    @Override
    public void saveTodoList(ImmutableTodoList todoList, String filePath) {
        logger.fine("Attempting to write to data file: " + filePath);
        
        try {
            todoListStorage.saveTodoList(todoList, filePath);
        } catch (IOException e) {
            raise(new DataSavingExceptionEvent(e));
        }
    }

}
