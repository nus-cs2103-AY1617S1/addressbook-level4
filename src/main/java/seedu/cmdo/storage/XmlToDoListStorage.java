package seedu.cmdo.storage;

import seedu.cmdo.commons.core.LogsCenter;
import seedu.cmdo.commons.exceptions.DataConversionException;
import seedu.cmdo.commons.util.FileUtil;
import seedu.cmdo.model.ReadOnlyToDoList;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Optional;
import java.util.logging.Logger;

/**
 * A class to access ToDoList data stored as an xml file on the hard disk.
 */
public class XmlToDoListStorage implements ToDoListStorage {

    private static final Logger logger = LogsCenter.getLogger(XmlToDoListStorage.class);

    private String filePath;

    public XmlToDoListStorage(String filePath){
        this.filePath = filePath;
    }

    public String getToDoListFilePath(){
        return filePath;
    }
    
    // @@author A0139661Y
    public void setToDoListFilePath(String filePath){
    	this.filePath = filePath;
    }

    /**
     * Similar to {@link #readToDoList()}
     * @param filePath location of the data. Cannot be null
     * @throws DataConversionException if the file is not in the correct format.
     */
    public Optional<ReadOnlyToDoList> readToDoList(String filePath) throws DataConversionException, FileNotFoundException {
        assert filePath != null;

        File toDoListFile = new File(filePath);

        if (!toDoListFile.exists()) {
            logger.info("ToDoList file "  + toDoListFile + " not found");
            return Optional.empty();
        }

        ReadOnlyToDoList toDoListOptional = XmlFileStorage.loadDataFromSaveFile(new File(filePath));

        return Optional.of(toDoListOptional);
    }

    /**
     * Similar to {@link #saveToDoList(ReadOnlyToDoList)}
     * @param filePath location of the data. Cannot be null
     */
    public void saveToDoList(ReadOnlyToDoList toDoList, String filePath) throws IOException {
        assert toDoList != null;
        assert filePath != null;

        File file = new File(filePath);
        FileUtil.createIfMissing(file);
        XmlFileStorage.saveDataToFile(file, new XmlSerializableToDoList(toDoList));
    }

    @Override
    public Optional<ReadOnlyToDoList> readToDoList() throws DataConversionException, IOException {
        return readToDoList(filePath);
    }

    @Override
    public void saveToDoList(ReadOnlyToDoList toDoList) throws IOException {
        saveToDoList(toDoList, filePath);
    }
}
