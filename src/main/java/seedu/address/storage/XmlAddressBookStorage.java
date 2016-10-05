package seedu.address.storage;

import seedu.address.commons.core.LogsCenter;
import seedu.address.commons.exceptions.DataConversionException;
import seedu.address.commons.util.FileUtil;
import seedu.address.model.ReadOnlyActivityManager;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Optional;
import java.util.logging.Logger;

/**
 * A class to access ActivityManager data stored as an xml file on the hard disk.
 */
public class XmlAddressBookStorage implements ActivityManagerStorage {

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
    public Optional<ReadOnlyActivityManager> readAddressBook(String filePath) throws DataConversionException, FileNotFoundException {
        assert filePath != null;

        File addressBookFile = new File(filePath);

        if (!addressBookFile.exists()) {
            logger.info("ActivityManager file "  + addressBookFile + " not found");
            return Optional.empty();
        }

        ReadOnlyActivityManager addressBookOptional = XmlFileStorage.loadDataFromSaveFile(new File(filePath));

        return Optional.of(addressBookOptional);
    }

    /**
     * Similar to {@link #saveAddressBook(ReadOnlyActivityManager)}
     * @param filePath location of the data. Cannot be null
     */
    public void saveAddressBook(ReadOnlyActivityManager addressBook, String filePath) throws IOException {
        assert addressBook != null;
        assert filePath != null;

        File file = new File(filePath);
        FileUtil.createIfMissing(file);
        XmlFileStorage.saveDataToFile(file, new XmlSerializableAddressBook(addressBook));
    }

    @Override
    public Optional<ReadOnlyActivityManager> readAddressBook() throws DataConversionException, IOException {
        return readAddressBook(filePath);
    }

    @Override
    public void saveAddressBook(ReadOnlyActivityManager addressBook) throws IOException {
        saveAddressBook(addressBook, filePath);
    }
}
