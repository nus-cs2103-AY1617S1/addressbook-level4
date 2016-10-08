package seedu.unburden.storage;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Optional;
import java.util.logging.Logger;

import seedu.unburden.commons.core.LogsCenter;
import seedu.unburden.commons.exceptions.DataConversionException;
import seedu.unburden.commons.util.FileUtil;
import seedu.unburden.model.ReadOnlyAddressBook;

/**
 * A class to access AddressBook data stored as an xml file on the hard disk.
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
     * Similar to {@link #readAddressBook()}
     * @param filePath location of the data. Cannot be null
     * @throws DataConversionException if the file is not in the correct format.
     */
    public Optional<ReadOnlyAddressBook> readTaskList(String filePath) throws DataConversionException, FileNotFoundException {
        assert filePath != null;

        File taskListFile = new File(filePath);

        if (!taskListFile.exists()) {
            logger.info("AddressBook file "  + taskListFile + " not found");
            return Optional.empty();
        }

        ReadOnlyAddressBook taskListOptional = XmlFileStorage.loadDataFromSaveFile(new File(filePath));

        return Optional.of(taskListOptional);
    }

    /**
     * Similar to {@link #saveAddressBook(ReadOnlyAddressBook)}
     * @param filePath location of the data. Cannot be null
     */
    public void saveTaskList(ReadOnlyAddressBook addressBook, String filePath) throws IOException {
        assert addressBook != null;
        assert filePath != null;

        File file = new File(filePath);
        FileUtil.createIfMissing(file);
        XmlFileStorage.saveDataToFile(file, new XmlSerializableAddressBook(addressBook));
    }

    @Override
    public Optional<ReadOnlyAddressBook> readTaskList() throws DataConversionException, IOException {
        return readTaskList(filePath);
    }

    @Override
    public void saveTaskList(ReadOnlyAddressBook addressBook) throws IOException {
        saveTaskList(addressBook, filePath);
    }
}
