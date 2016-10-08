package seedu.menion.storage;

import seedu.menion.commons.core.LogsCenter;
import seedu.menion.commons.exceptions.DataConversionException;
import seedu.menion.commons.util.FileUtil;
import seedu.menion.model.ReadOnlyActivityManager;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Optional;
import java.util.logging.Logger;

/**
 * A class to access ActivityManager data stored as an xml file on the hard disk.
 */
public class XmlActivityManagerStorage implements ActivityManagerStorage {

    private static final Logger logger = LogsCenter.getLogger(XmlActivityManagerStorage.class);

    private String filePath;

    public XmlActivityManagerStorage(String filePath){
        this.filePath = filePath;
    }

    public String getActivityManagerFilePath(){
        return filePath;
    }

    /**
     * Similar to {@link #readActivityManager()}
     * @param filePath location of the data. Cannot be null
     * @throws DataConversionException if the file is not in the correct format.
     */
    public Optional<ReadOnlyActivityManager> readActivityManager(String filePath) throws DataConversionException, FileNotFoundException {
        assert filePath != null;

        File activityManagerFile = new File(filePath);

        if (!activityManagerFile.exists()) {
            logger.info("ActivityManager file "  + activityManagerFile + " not found");
            return Optional.empty();
        }

        ReadOnlyActivityManager activityManagerOptional = XmlFileStorage.loadDataFromSaveFile(new File(filePath));

        return Optional.of(activityManagerOptional);
    }

    /**
     * Similar to {@link #saveActivityManager(ReadOnlyActivityManager)}
     * @param filePath location of the data. Cannot be null
     */
    public void saveActivityManager(ReadOnlyActivityManager activityManager, String filePath) throws IOException {
        assert activityManager != null;
        assert filePath != null;

        File file = new File(filePath);
        FileUtil.createIfMissing(file);
        XmlFileStorage.saveDataToFile(file, new XmlSerializableActivityManager(activityManager));
    }

    @Override
    public Optional<ReadOnlyActivityManager> readActivityManager() throws DataConversionException, IOException {
        return readActivityManager(filePath);
    }

    @Override
    public void saveActivityManager(ReadOnlyActivityManager taskManager) throws IOException {
        saveActivityManager(taskManager, filePath);
    }
}
