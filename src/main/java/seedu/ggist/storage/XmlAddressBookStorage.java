package seedu.ggist.storage;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Optional;
import java.util.logging.Logger;

import seedu.ggist.commons.core.LogsCenter;
import seedu.ggist.commons.exceptions.DataConversionException;
import seedu.ggist.commons.util.FileUtil;
import seedu.ggist.model.ReadOnlyTaskManager;

/**
 * A class to access TaskManager data stored as an xml file on the hard disk.
 */
public class XmlAddressBookStorage implements AddressBookStorage {

    private static final Logger logger = LogsCenter.getLogger(XmlAddressBookStorage.class);

    private String filePath;

    public XmlAddressBookStorage(String filePath){
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
    public Optional<ReadOnlyTaskManager> readAddressBook(String filePath) throws DataConversionException, FileNotFoundException {
        assert filePath != null;

        File addressBookFile = new File(filePath);

        if (!addressBookFile.exists()) {
            logger.info("TaskManager file "  + addressBookFile + " not found");
            return Optional.empty();
        }

        ReadOnlyTaskManager addressBookOptional = XmlFileStorage.loadDataFromSaveFile(new File(filePath));

        return Optional.of(addressBookOptional);
    }

    /**
     * Similar to {@link #saveAddressBook(ReadOnlyTaskManager)}
     * @param filePath location of the data. Cannot be null
     */
    public void saveAddressBook(ReadOnlyTaskManager addressBook, String filePath) throws IOException {
        assert addressBook != null;
        assert filePath != null;

        File file = new File(filePath);
        FileUtil.createIfMissing(file);
        XmlFileStorage.saveDataToFile(file, new XmlSerializableAddressBook(addressBook));
    }

    @Override
    public Optional<ReadOnlyTaskManager> readAddressBook() throws DataConversionException, IOException {
        return readAddressBook(filePath);
    }

    @Override
    public void saveAddressBook(ReadOnlyTaskManager addressBook) throws IOException {
        saveAddressBook(addressBook, filePath);
    }
}
