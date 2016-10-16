package seedu.todo.storage;

import java.io.IOException;
import java.util.Optional;

import seedu.todo.commons.exceptions.DataConversionException;
import seedu.todo.commons.util.JsonUtil;
import seedu.todo.model.UserPrefs;

/**
 * A class to access UserPrefs stored in the hard disk as a json file
 */
public class UserPrefsStorage implements FixedStorage<UserPrefs> {

    private String filePath;

    public UserPrefsStorage(String filePath){
        this.filePath = filePath;
    }

    @Override
    public Optional<UserPrefs> read() throws DataConversionException, IOException {
        return JsonUtil.readJsonFile(filePath, UserPrefs.class);
    }

    @Override
    public void save(UserPrefs userPrefs) throws IOException {
        JsonUtil.saveJsonFile(userPrefs, filePath);
    }

}
