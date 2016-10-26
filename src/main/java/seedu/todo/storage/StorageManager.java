package seedu.todo.storage;

import java.io.IOException;

import seedu.todo.commons.core.ComponentManager;
import seedu.todo.commons.exceptions.DataConversionException;
import seedu.todo.model.UserPrefs;

/**
 * Manages storage of user preferences in local storage.
 */
public class StorageManager extends ComponentManager implements Storage {

    private UserPrefsStorage userPrefsStorage;

    public StorageManager(UserPrefsStorage userPrefsStorage) {
        super();
        this.userPrefsStorage = userPrefsStorage;
    }

    public StorageManager(String userPrefsFilePath) {
        this(new UserPrefsStorage(userPrefsFilePath));
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
