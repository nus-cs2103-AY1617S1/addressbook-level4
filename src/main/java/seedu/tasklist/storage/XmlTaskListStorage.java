package seedu.tasklist.storage;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Optional;
import java.util.logging.Logger;

import seedu.tasklist.commons.core.LogsCenter;
import seedu.tasklist.commons.exceptions.DataConversionException;
import seedu.tasklist.commons.util.FileUtil;
import seedu.tasklist.model.ReadOnlyTaskList;

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

    /**
     * Similar to {@link #readTaskList()}
     * @param filePath location of the data. Cannot be null
     * @throws DataConversionException if the file is not in the correct format.
     */
    public Optional<ReadOnlyTaskList> readTaskList(String filePath) throws DataConversionException, FileNotFoundException {
        
    	assert filePath != null;
    	if(filePath!=null){
        File taskListFile = new File(filePath);

        if (!taskListFile.exists()) {
            logger.info("TaskList file "  + taskListFile + " not found");
            return Optional.empty();
        }

        ReadOnlyTaskList taskListOptional = XmlFileStorage.loadDataFromSaveFile(new File(filePath));
    	

        return Optional.of(taskListOptional);
    	}
    	else return Optional.empty();
    }

    /**
     * Similar to {@link #saveTaskList(ReadOnlyTaskList)}
     * @param filePath location of the data. Cannot be null
     */
    public void saveTaskList(ReadOnlyTaskList taskList, String filePath) throws IOException {
        assert taskList != null;
        assert filePath != null;

        File file = new File(filePath);
        FileUtil.createIfMissing(file);
        XmlFileStorage.saveDataToFile(file, new XmlSerializableTaskList(taskList));
    }

    @Override
    public Optional<ReadOnlyTaskList> readTaskList() throws DataConversionException, IOException {
        return readTaskList(filePath);
    }

    @Override
    public void saveTaskList(ReadOnlyTaskList taskList) throws IOException {
        saveTaskList(taskList, filePath);
    }
}
