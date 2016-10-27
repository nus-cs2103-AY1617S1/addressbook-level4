package seedu.address.storage;

import seedu.address.commons.core.LogsCenter;
import seedu.address.commons.exceptions.DataConversionException;
import seedu.address.commons.util.FileUtil;
import seedu.address.model.ReadOnlyTaskManager;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.nio.file.Paths;
import java.util.Optional;
import java.util.logging.Logger;

/**
 * A class to access TaskManager data stored as an xml file on the hard disk.
 */
public class XmlTaskManagerStorage implements TaskManagerStorage {

    private static final Logger logger = LogsCenter.getLogger(XmlTaskManagerStorage.class);

    private String filePath;
    private String previousFilePath;

    public XmlTaskManagerStorage(String filePath){
        this.previousFilePath = filePath;
        this.filePath = filePath;
    }

    @Override
    public String getTaskManagerFilePath(){
        return filePath;
    }
    
    @Override
    public String getTaskManagerPreviousFilePath(){
        return previousFilePath;
    }
    
    @Override
    public void setTaskManagerFilePath(String filePath) {
        this.previousFilePath = this.filePath;
        this.filePath = filePath;
    }

    /**
     * Similar to {@link #readTaskManager()}
     * @param filePath location of the data. Cannot be null
     * @throws DataConversionException if the file is not in the correct format.
     */
    @Override
    public Optional<ReadOnlyTaskManager> readTaskManager(String filePath) throws DataConversionException, FileNotFoundException {
        assert filePath != null;

        File addressBookFile = new File(filePath);

        if (!addressBookFile.exists()) {
            logger.info("TaskManager file "  + addressBookFile + " not found");
            return Optional.empty();
        }

        ReadOnlyTaskManager addressBookOptional = XmlFileStorage.loadDataFromSaveFile(new File(filePath));

        return Optional.of(addressBookOptional);
    }

    /**
     * Similar to {@link #saveTaskManager(ReadOnlyTaskManager)}
     * @param filePath location of the data. Cannot be null
     */
    @Override
    public void saveTaskManager(ReadOnlyTaskManager addressBook, String filePath) throws IOException {
        assert addressBook != null;
        assert filePath != null;

        File file = new File(filePath);
        FileUtil.createIfMissing(file);
        XmlFileStorage.saveDataToFile(file, new XmlSerializableTaskManager(addressBook));
    }
    
    /**
     * Similar to {@link #deleteTaskManager()}
     */
    @Override
    public void deleteTaskManager(String filePath) throws IOException {
        assert filePath != null;

        XmlFileStorage.deleteFile(Paths.get(filePath));
    }

    @Override
    public Optional<ReadOnlyTaskManager> readTaskManager() throws DataConversionException, IOException {
        return readTaskManager(filePath);
    }
    
    @Override
    public void saveTaskManager(ReadOnlyTaskManager addressBook) throws IOException {
        saveTaskManager(addressBook, filePath);
    }
    
    @Override
    public void deleteTaskManager() throws IOException {
        deleteTaskManager(filePath);
    }
    
}
