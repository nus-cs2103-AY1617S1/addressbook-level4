package seedu.todoList.storage;

import com.google.common.eventbus.Subscribe;

import seedu.todoList.commons.core.ComponentManager;
import seedu.todoList.commons.core.LogsCenter;
import seedu.todoList.commons.events.model.TodoListChangedEvent;
import seedu.todoList.commons.events.storage.DataSavingExceptionEvent;
import seedu.todoList.commons.exceptions.DataConversionException;
import seedu.todoList.model.ReadOnlyTodoList;
import seedu.todoList.model.UserPrefs;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Optional;
import java.util.logging.Logger;

/**
 * Manages storage of TodoList data in local storage.
 */
public class StorageManager extends ComponentManager implements Storage {

    private static final Logger logger = LogsCenter.getLogger(StorageManager.class);
    private TodoListStorage TodoListStorage;
    private UserPrefsStorage userPrefsStorage;


    public StorageManager(TodoListStorage TodoListStorage, UserPrefsStorage userPrefsStorage) {
        super();
        this.TodoListStorage = TodoListStorage;
        this.userPrefsStorage = userPrefsStorage;
    }

    public StorageManager(String TodoListFilePath, String userPrefsFilePath) {
        this(new XmlTodoListStorage(TodoListFilePath), new JsonUserPrefsStorage(userPrefsFilePath));
    }

    // ================ UserPrefs methods ==============================

    @Override
    public Optional<UserPrefs> readUserPrefs() throws DataConversionException, IOException {
        return userPrefsStorage.readUserPrefs();
    }

    @Override
    public void saveUserPrefs(UserPrefs userPrefs) throws IOException {
        userPrefsStorage.saveUserPrefs(userPrefs);
    }


    // ================ TodoList methods ==============================

    @Override
    public String getTodoListFilePath() {
        return TodoListStorage.getTodoListFilePath();
    }

    @Override
    public Optional<ReadOnlyTodoList> readTodoList() throws DataConversionException, IOException {
        return readTodoList(TodoListStorage.getTodoListFilePath());
    }

    @Override
    public Optional<ReadOnlyTodoList> readTodoList(String filePath) throws DataConversionException, IOException {
        logger.fine("Attempting to read data from file: " + filePath);
        return TodoListStorage.readTodoList(filePath);
    }

    @Override
    public void saveTodoList(ReadOnlyTodoList TodoList) throws IOException {
        saveTodoList(TodoList, TodoListStorage.getTodoListFilePath());
    }

    @Override
    public void saveTodoList(ReadOnlyTodoList TodoList, String filePath) throws IOException {
        logger.fine("Attempting to write to data file: " + filePath);
        TodoListStorage.saveTodoList(TodoList, filePath);
    }


    @Override
    @Subscribe
    public void handleTodoListChangedEvent(TodoListChangedEvent event) {
        logger.info(LogsCenter.getEventHandlingLogMessage(event, "Local data changed, saving to file"));
        try {
            saveTodoList(event.data);
        } catch (IOException e) {
            raise(new DataSavingExceptionEvent(e));
        }
    }

}
