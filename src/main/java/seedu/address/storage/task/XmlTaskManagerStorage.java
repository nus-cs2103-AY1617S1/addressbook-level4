package seedu.address.storage.task;

import seedu.address.commons.collections.UniqueItemCollection;
import seedu.address.commons.core.LogsCenter;
import seedu.address.commons.exceptions.DataConversionException;
import seedu.address.commons.util.FileUtil;
import seedu.address.model.ReadOnlyAddressBook;
import seedu.address.model.task.Description;
import seedu.address.model.task.Task;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Optional;
import java.util.logging.Logger;

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

    /**
     * Similar to {@link #UniqueItemCollection<Task>}
     * @param filePath location of the data. Cannot be null
     * @throws DataConversionException if the file is not in the correct format.
     */
    public Optional<UniqueItemCollection<Task>> readTaskManager(String filePath) throws DataConversionException, FileNotFoundException {
        assert filePath != null;

        File taskManagerFile = new File(filePath);

        if (!taskManagerFile.exists()) {
            logger.info("TaskManager file "  + taskManagerFile + " not found");
            return Optional.empty();
        }
        
        UniqueItemCollection<Task> taskManagerOptional = XmlTaskFileStorage.loadDataFromSaveFile(new File(filePath));

        return Optional.of(taskManagerOptional);
    }

    /**
     * Similar to {@link #saveAddressBook(UniqueItemCollection<Task>)}
     * @param filePath location of the data. Cannot be null
     */
    public void saveTaskManager(UniqueItemCollection<Task> taskManager, String filePath) throws IOException {
        assert taskManager != null;
        assert filePath != null;

        File file = new File(filePath);
        FileUtil.createIfMissing(file);
        XmlTaskFileStorage.saveDataToFile(file, new XmlSerializableTaskManager(taskManager));
    }

    @Override
    public Optional<UniqueItemCollection<Task>> readTaskManager() throws DataConversionException, IOException {
        return readTaskManager(filePath);
    }

    @Override
    public void saveTaskManager(UniqueItemCollection<Task> taskManager) throws IOException {
        saveTaskManager(taskManager, filePath);
    }
}
