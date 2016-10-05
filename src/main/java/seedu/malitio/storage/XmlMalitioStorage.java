package seedu.malitio.storage;

import seedu.malitio.commons.core.LogsCenter;
import seedu.malitio.commons.exceptions.DataConversionException;
import seedu.malitio.commons.util.FileUtil;
import seedu.malitio.model.ReadOnlyMalitio;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Optional;
import java.util.logging.Logger;

/**
 * A class to access AddressBook data stored as an xml file on the hard disk.
 */
public class XmlMalitioStorage implements MalitioStorage {

    private static final Logger logger = LogsCenter.getLogger(XmlMalitioStorage.class);

    private String filePath;

    public XmlMalitioStorage(String filePath){
        this.filePath = filePath;
    }

    public String getMalitioFilePath(){
        return filePath;
    }

    /**
     * Similar to {@link #readMalitio()}
     * @param filePath location of the data. Cannot be null
     * @throws DataConversionException if the file is not in the correct format.
     */
    public Optional<ReadOnlyMalitio> readMalitio(String filePath) throws DataConversionException, FileNotFoundException {
        assert filePath != null;

        File addressBookFile = new File(filePath);

        if (!addressBookFile.exists()) {
            logger.info("AddressBook file "  + addressBookFile + " not found");
            return Optional.empty();
        }

        ReadOnlyMalitio addressBookOptional = XmlFileStorage.loadDataFromSaveFile(new File(filePath));

        return Optional.of(addressBookOptional);
    }

    /**
     * Similar to {@link #saveAddressBook(ReadOnlyMalitio)}
     * @param filePath location of the data. Cannot be null
     */
    public void saveAddressBook(ReadOnlyMalitio addressBook, String filePath) throws IOException {
        assert addressBook != null;
        assert filePath != null;

        File file = new File(filePath);
        FileUtil.createIfMissing(file);
        XmlFileStorage.saveDataToFile(file, new XmlSerializableMalitio(addressBook));
    }

    @Override
    public Optional<ReadOnlyMalitio> readMalitio() throws DataConversionException, IOException {
        return readMalitio(filePath);
    }

    @Override
    public void saveAddressBook(ReadOnlyMalitio addressBook) throws IOException {
        saveAddressBook(addressBook, filePath);
    }
}
