package seedu.agendum.storage;

import seedu.agendum.commons.core.LogsCenter;
import seedu.agendum.commons.exceptions.DataConversionException;
import seedu.agendum.commons.util.FileUtil;
import seedu.agendum.commons.util.StringUtil;
import seedu.agendum.model.ReadOnlyToDoList;

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

    /**
     * Similar to {@link #readToDoList()}
     * @param filePath location of the data. Cannot be null
     * @throws DataConversionException if the file is not in the correct format.
     */
    public Optional<ReadOnlyToDoList> readToDoList(String filePath) throws DataConversionException {
        assert filePath != null;
        File toDoListFile = new File(filePath);

        ReadOnlyToDoList toDoListOptional;
        try {
            toDoListOptional = XmlFileStorage.loadDataFromSaveFile(new File(filePath));
        } catch (FileNotFoundException e) {
            logger.info("ToDoList file "  + toDoListFile + " not found");
            return Optional.empty();
        }

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

    @Override
    public void setToDoListFilePath(String filePath) {
        assert StringUtil.isValidPathToFile(filePath);
        this.filePath = filePath;
    }
}
