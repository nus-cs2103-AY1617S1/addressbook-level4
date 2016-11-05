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
    
    private FilePathManager filePathManager;

    private String filePath;

    public XmlTaskManagerStorage(String filePath){
        this.filePath = filePath;
        filePathManager = new FilePathManager(new FilePath(filePath, false));
    }

    @Override
    public String getTaskManagerFilePath(){
        return filePath;
    }
    
    //@@author A0146123R
    @Override
    public FilePath getTaskManagerPreviousFilePath(){
        return filePathManager.getPreviousFilePath();
    }
    
    @Override
    public FilePath getTaskManagerNextFilePath(){
        return filePathManager.getNextFilePath();
    }
    
    @Override
    public void saveTaskManagerFilePath(FilePath filePath){
        filePathManager.saveFilePath(filePath);
    }
    
    @Override
    public void setTaskManagerFilePath(FilePath newfilePath) throws IOException  {
        if (newfilePath.isToClear()) {
            logger.info("Attempting to delete the data file: " + this.filePath);
            deleteTaskManager();
        }
        logger.info("Saving to new file: " + newfilePath.getPath());
        this.filePath = newfilePath.getPath();
        assert this.filePath != null;
    }
    //@@author

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
    
    @Override
    public Optional<ReadOnlyTaskManager> readTaskManager() throws DataConversionException, IOException {
        return readTaskManager(filePath);
    }
    
    @Override
    public void saveTaskManager(ReadOnlyTaskManager addressBook) throws IOException {
        saveTaskManager(addressBook, filePath);
    }
    
    //@@author A0146123R
    /**
     * Delete the storage file.
     * @throws IOException if there was any problem deleting the file.
     */
    private void deleteTaskManager() throws IOException {
        assert filePath != null;
        XmlFileStorage.deleteFile(Paths.get(filePath));
    }
    //@@author
    
}
