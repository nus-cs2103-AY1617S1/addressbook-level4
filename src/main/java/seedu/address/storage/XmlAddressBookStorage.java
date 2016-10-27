package seedu.address.storage;

import seedu.address.commons.core.LogsCenter;
import seedu.address.commons.exceptions.DataConversionException;
import seedu.address.commons.exceptions.IllegalValueException;
import seedu.address.commons.util.FileUtil;
import seedu.address.model.ReadOnlyLifeKeeper;
import seedu.address.model.UserPrefs;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Optional;
import java.util.logging.Logger;

/**
 * A class to access AddressBook data stored as an xml file on the hard disk.
 */
public class XmlAddressBookStorage implements AddressBookStorage {

    private static final Logger logger = LogsCenter.getLogger(XmlAddressBookStorage.class);

    private static String filePath;

    public XmlAddressBookStorage(String filePath){
        this.filePath = filePath;
    }
    
    public XmlAddressBookStorage(){}
    
    //@@author A0125680H
    /**
     * Sets the storage location of the activity data to the new file path specified
     * @param filePath
     */
    public static void setAddressBookFilePath(String filePath){
        XmlAddressBookStorage.filePath = filePath;
        UserPrefs.setDataFilePath(filePath);
    }

    public String getAddressBookFilePath(){
        return filePath;
    }

    /**
     * Similar to {@link #readAddressBook()}
     * @param filePath location of the data. Cannot be null
     * @throws DataConversionException if the file is not in the correct format.
     */
    public Optional<ReadOnlyLifeKeeper> readAddressBook(String filePath) throws DataConversionException, FileNotFoundException {
        assert filePath != null;

        File addressBookFile = new File(filePath);

        if (!addressBookFile.exists()) {
            logger.info("AddressBook file "  + addressBookFile + " not found");
            return Optional.empty();
        }

        ReadOnlyLifeKeeper addressBookOptional = XmlFileStorage.loadDataFromSaveFile(new File(filePath));

        return Optional.of(addressBookOptional);
    }

    /**
     * Similar to {@link #saveAddressBook(ReadOnlyLifeKeeper)}
     * @param filePath location of the data. Cannot be null
     */
    public void saveAddressBook(ReadOnlyLifeKeeper addressBook, String filePath) throws IOException {
        assert addressBook != null;
        assert filePath != null;

        File file = new File(filePath);
        FileUtil.createIfMissing(file);
        XmlFileStorage.saveDataToFile(file, new XmlSerializableAddressBook(addressBook));
    }

    @Override
    public Optional<ReadOnlyLifeKeeper> readAddressBook() throws DataConversionException, IOException {
        return readAddressBook(filePath);
    }

    @Override
    public void saveAddressBook(ReadOnlyLifeKeeper addressBook) throws IOException {
        saveAddressBook(addressBook, filePath);
    }
}
