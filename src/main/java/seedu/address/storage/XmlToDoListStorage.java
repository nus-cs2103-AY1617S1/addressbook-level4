package seedu.address.storage;

import seedu.address.commons.core.LogsCenter;
import seedu.address.commons.exceptions.DataConversionException;
import seedu.address.commons.util.FileUtil;
import seedu.address.model.ReadOnlyToDoList;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Optional;
import java.util.logging.Logger;

/**
 * A class to access to-do list data stored as an xml file on the hard disk.
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
     * Reads the to-do list data at {@param filePath}
     * @param filePath location to read the to-do list. Must be non-null
     * @throws DataConversionException if the file is not in the correct format.
     */
    public Optional<ReadOnlyToDoList> readToDoList(String filePath) throws DataConversionException, FileNotFoundException {
        assert filePath != null;

        File toDoListFile = new File(filePath);

        if (!toDoListFile.exists()) {
            logger.info("To-do list data file "  + toDoListFile + " not found");
            return Optional.empty();
        }

        ReadOnlyToDoList toDoListOptional = XmlFileStorage.loadDataFromSaveFile(new File(filePath));

        return Optional.of(toDoListOptional);
    }

    /**
     * Saves the to-do list data to {@param filePath}
     * @param toDoList to-do list to save. Must be non-null
     * @param filePath location to save the to-do list. Must be non-null
     */
    public void saveToDoList(ReadOnlyToDoList toDoList, String filePath) throws IOException {
        assert toDoList != null;
        assert filePath != null;

        File file = new File(filePath);
        FileUtil.createIfMissing(file);
        XmlFileStorage.saveDataToFile(file, new XmlSerializableToDoList(toDoList));
    }

    /**
     * Reads the to-do list from storage
     */
    @Override
    public Optional<ReadOnlyToDoList> readToDoList() throws DataConversionException, IOException {
        return readToDoList(filePath);
    }

    /**
     * Saves the to-do list to storage
     * @param toDoList to-do list to save. Must be non-null
     */
    @Override
    public void saveToDoList(ReadOnlyToDoList toDoList) throws IOException {
        saveToDoList(toDoList, filePath);
    }
}
