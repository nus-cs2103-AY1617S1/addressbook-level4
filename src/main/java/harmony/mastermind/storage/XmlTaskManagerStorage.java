package harmony.mastermind.storage;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.Optional;
import java.util.logging.Logger;

import harmony.mastermind.commons.core.LogsCenter;
import harmony.mastermind.commons.exceptions.DataConversionException;
import harmony.mastermind.commons.exceptions.FolderDoesNotExistException;
import harmony.mastermind.commons.util.FileUtil;
import harmony.mastermind.model.ReadOnlyTaskManager;

/**
 * A class to access TaskManager data stored as an xml file on the hard disk.
 */
public class XmlTaskManagerStorage implements TaskManagerStorage {

    private static final Logger logger = LogsCenter.getLogger(XmlTaskManagerStorage.class);

    private String filePath;

    public XmlTaskManagerStorage(String filePath){
        this.filePath = filePath;
    }

    public String getTaskManagerFilePath(){
        return filePath;
    }
    
    //@author A0139194X
    public void setTaskManagerFilePath(String newFilePath){
        logger.fine("Changing new file path from \"" + this.filePath + "\" to " + newFilePath);
        this.filePath = newFilePath;
    }

    /**@@author
     * Similar to {@link #readTaskManager()}
     * @param filePath location of the data. Cannot be null
     * @throws DataConversionException if the file is not in the correct format.
     */
    public Optional<ReadOnlyTaskManager> readTaskManager(String filePath) throws DataConversionException, FileNotFoundException {
        assert filePath != null;

        File taskManagerFile = new File(filePath);

        if (!taskManagerFile.exists()) {
            logger.info("TaskManager file "  + taskManagerFile + " not found");
            return Optional.empty();
        }

        ReadOnlyTaskManager taskManagerOptional = XmlFileStorage.loadDataFromSaveFile(new File(filePath));

        return Optional.of(taskManagerOptional);
    }

    /**
     * Similar to {@link #saveTaskManager(ReadOnlyTaskManager)}
     * @param filePath location of the data. Cannot be null
     */
    public void saveTaskManager(ReadOnlyTaskManager taskManager, String filePath) throws IOException {
        assert taskManager != null;
        assert filePath != null;

        File file = new File(filePath);
        FileUtil.createIfMissing(file);
        XmlFileStorage.saveDataToFile(file, new XmlSerializableTaskManager(taskManager));
    }

    @Override
    public Optional<ReadOnlyTaskManager> readTaskManager() throws DataConversionException, IOException {
        return readTaskManager(filePath);
    }

    @Override
    public void saveTaskManager(ReadOnlyTaskManager taskManager) throws IOException {
        saveTaskManager(taskManager, filePath);
    }

    //@author A0139194X
    public void migrateIntoNewFolder(String oldPath, String newPath) throws IOException {
        assert oldPath != null;
        assert newPath != null;
        Path oldDirectory = Paths.get(oldPath);
        Path newDirectory = Paths.get(newPath);
        File newFile = new File(newPath);
        FileUtil.createIfMissing(newFile);
        Files.copy(oldDirectory, newDirectory, StandardCopyOption.REPLACE_EXISTING);
        deleteFile(oldPath);
    }
    
    //@author A0139194X
    public void deleteFile(String filePath) {
        assert filePath != null;
        File toDelete = new File(filePath);
        if (toDelete.delete()) {
            logger.fine("Deleted " + filePath);
        } else {
            logger.warning("Failed to delete " + filePath);
        }
    }
}
