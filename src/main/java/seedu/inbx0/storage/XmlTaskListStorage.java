package seedu.inbx0.storage;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Optional;
import java.util.logging.Logger;

import seedu.inbx0.commons.core.LogsCenter;
import seedu.inbx0.commons.exceptions.DataConversionException;
import seedu.inbx0.commons.util.FileUtil;
import seedu.inbx0.model.ReadOnlyTaskList;

/**
 * A class to access TaskList data stored as an xml file on the hard disk.
 */
public class XmlTaskListStorage implements TaskListStorage {

    private static final Logger logger = LogsCenter.getLogger(XmlTaskListStorage.class);

    private String filePath;

    public XmlTaskListStorage(String filePath){
        this.filePath = filePath;
    }

    public String getAddressBookFilePath(){
        return filePath;
    }

    /**
     * Similar to {@link #readAddressBook()}
     * @param filePath location of the data. Cannot be null
     * @throws DataConversionException if the file is not in the correct format.
     */
    public Optional<ReadOnlyTaskList> readAddressBook(String filePath) throws DataConversionException, FileNotFoundException {
        assert filePath != null;

        File addressBookFile = new File(filePath);

        if (!addressBookFile.exists()) {
            logger.info("TaskList file "  + addressBookFile + " not found");
            return Optional.empty();
        }

        ReadOnlyTaskList addressBookOptional = XmlFileStorage.loadDataFromSaveFile(new File(filePath));

        return Optional.of(addressBookOptional);
    }

    /**
     * Similar to {@link #saveAddressBook(ReadOnlyTaskList)}
     * @param filePath location of the data. Cannot be null
     */
    public void saveAddressBook(ReadOnlyTaskList addressBook, String filePath) throws IOException {
        assert addressBook != null;
        assert filePath != null;

        File file = new File(filePath);
        FileUtil.createIfMissing(file);
        XmlFileStorage.saveDataToFile(file, new XmlSerializableTaskList(addressBook));
    }

    @Override
    public Optional<ReadOnlyTaskList> readAddressBook() throws DataConversionException, IOException {
        return readAddressBook(filePath);
    }

    @Override
    public void saveAddressBook(ReadOnlyTaskList addressBook) throws IOException {
        saveAddressBook(addressBook, filePath);
    }
}
