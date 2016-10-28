package seedu.todolist.storage;

import seedu.todolist.commons.core.LogsCenter;
import seedu.todolist.commons.exceptions.DataConversionException;
import seedu.todolist.commons.util.FileUtil;
import seedu.todolist.model.ReadOnlyToDoList;

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
    public Optional<ReadOnlyToDoList> readToDoList(String filePath) throws DataConversionException, FileNotFoundException {
        assert filePath != null;

        File ToDoListFile = new File(filePath);

        if (!ToDoListFile.exists()) {
            logger.info("ToDoList file "  + ToDoListFile + " not found");
            return Optional.empty();
        }

        ReadOnlyToDoList ToDoListOptional = XmlFileStorage.loadDataFromSaveFile(new File(filePath));

        return Optional.of(ToDoListOptional);
    }

    /**
     * Similar to {@link #saveToDoList(ReadOnlyToDoList)}
     * @param filePath location of the data. Cannot be null
     */
    public void saveToDoList(ReadOnlyToDoList ToDoList, String filePath) throws IOException {
        assert ToDoList != null;
        assert filePath != null;

        File file = new File(filePath);
        FileUtil.createIfMissing(file);
        XmlFileStorage.saveDataToFile(file, new XmlSerializableToDoList(ToDoList));
    }

    @Override
    public Optional<ReadOnlyToDoList> readToDoList() throws DataConversionException, IOException {
        return readToDoList(filePath);
    }

    @Override
    public void saveToDoList(ReadOnlyToDoList ToDoList) throws IOException {
        saveToDoList(ToDoList, filePath);
    }
}
