package seedu.address.storage;

import seedu.address.commons.core.LogsCenter;
import seedu.address.commons.exceptions.DataConversionException;
import seedu.address.commons.util.FileUtil;
import seedu.address.model.ReadOnlyTaskMaster;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Optional;
import java.util.logging.Logger;

/**
 * A class to access TaskList data stored as an xml file on the hard disk.
 */
public class XmlTaskListStorage implements TaskListStorage {

    private static final Logger logger = LogsCenter.getLogger(XmlTaskListStorage.class);

    private String filePath;

    public XmlTaskListStorage(String filePath){
        this.filePath = filePath;
    }

    public String getTaskListFilePath(){
        return filePath;
    }
    
    public void setTaskListFilePath(String filePath){
        this.filePath = filePath;
    }

    /**
     * Similar to {@link #readTaskList()}
     * @param filePath location of the data. Cannot be null
     * @throws DataConversionException if the file is not in the correct format.
     */
    public Optional<ReadOnlyTaskMaster> readTaskList(String filePath) throws DataConversionException, FileNotFoundException {
        assert filePath != null;

        File taskListFile = new File(filePath);

        if (!taskListFile.exists()) {
            logger.info("TaskList file "  + taskListFile + " not found");
            return Optional.empty();
        }

        ReadOnlyTaskMaster taskListOptional = XmlFileStorage.loadDataFromSaveFile(new File(filePath));

        return Optional.of(taskListOptional);
    }

    /**
     * Similar to {@link #saveTaskList(ReadOnlyTaskMaster)}
     * @param filePath location of the data. Cannot be null
     */
    public void saveTaskList(ReadOnlyTaskMaster taskList, String filePath) throws IOException {
        assert taskList != null;
        assert filePath != null;

        File file = new File(filePath);
        FileUtil.createIfMissing(file);
        XmlFileStorage.saveDataToFile(file, new XmlSerializableTaskList(taskList));
    }

    @Override
    public Optional<ReadOnlyTaskMaster> readTaskList() throws DataConversionException, IOException {
        return readTaskList(filePath);
    }

    @Override
    public void saveTaskList(ReadOnlyTaskMaster taskList) throws IOException {
        saveTaskList(taskList, filePath);
    }
    
}
