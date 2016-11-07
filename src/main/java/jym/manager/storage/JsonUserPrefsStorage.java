<<<<<<< HEAD:src/main/java/jym/manager/storage/JsonUserPrefsStorage.java
package jym.manager.storage;
=======
package seedu.address.storage;

import seedu.address.commons.exceptions.DataConversionException;
import seedu.address.commons.util.JsonUtil;
import seedu.address.model.UserPrefs;
>>>>>>> nus-cs2103-AY1617S1/master:src/main/java/seedu/address/storage/JsonUserPrefsStorage.java

import java.io.IOException;
import java.util.Optional;

import jym.manager.commons.core.LogsCenter;
import jym.manager.commons.exceptions.DataConversionException;
import jym.manager.commons.util.FileUtil;
import jym.manager.model.UserPrefs;

/**
 * A class to access UserPrefs stored in the hard disk as a json file
 */
public class JsonUserPrefsStorage implements UserPrefsStorage {

    private String filePath;

    public JsonUserPrefsStorage(String filePath){
        this.filePath = filePath;
    }

    @Override
    public Optional<UserPrefs> readUserPrefs() throws DataConversionException, IOException {
        return readUserPrefs(filePath);
    }

    /**
     * Similar to {@link #readUserPrefs()}
     * @param prefsFilePath location of the data. Cannot be null.
     * @throws DataConversionException if the file format is not as expected.
     */
    public Optional<UserPrefs> readUserPrefs(String prefsFilePath) throws DataConversionException {
        return JsonUtil.readJsonFile(prefsFilePath, UserPrefs.class);
    }

    @Override
    public void saveUserPrefs(UserPrefs userPrefs) throws IOException {
        JsonUtil.saveJsonFile(userPrefs, filePath);
    }

}
