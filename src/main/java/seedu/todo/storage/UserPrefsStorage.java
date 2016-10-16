package seedu.todo.storage;

import java.io.IOException;

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
    public UserPrefs read() throws DataConversionException {
        return JsonUtil.readJsonFile(filePath, UserPrefs.class)
            .orElse(new UserPrefs());
    }

    @Override
    public void save(UserPrefs userPrefs) throws IOException {
        JsonUtil.saveJsonFile(userPrefs, filePath);
    }

}
