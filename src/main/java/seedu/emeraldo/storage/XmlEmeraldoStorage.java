package seedu.emeraldo.storage;

import seedu.address.commons.util.FileUtil;
import seedu.address.model.ReadOnlyEmeraldo;
import seedu.emeraldo.commons.core.LogsCenter;
import seedu.emeraldo.commons.exceptions.DataConversionException;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Optional;
import java.util.logging.Logger;

/**
 * A class to access AddressBook data stored as an xml file on the hard disk.
 */
public class XmlEmeraldoStorage implements EmeraldoStorage {

    private static final Logger logger = LogsCenter.getLogger(XmlEmeraldoStorage.class);

    private String filePath;

    public XmlEmeraldoStorage(String filePath){
        this.filePath = filePath;
    }

    public String getEmeraldoFilePath(){
        return filePath;
    }

    /**
     * Similar to {@link #readEmeraldo()}
     * @param filePath location of the data. Cannot be null
     * @throws DataConversionException if the file is not in the correct format.
     */
    public Optional<ReadOnlyEmeraldo> readEmeraldo(String filePath) throws DataConversionException, FileNotFoundException {
        assert filePath != null;

        File addressBookFile = new File(filePath);

        if (!addressBookFile.exists()) {
            logger.info("AddressBook file "  + addressBookFile + " not found");
            return Optional.empty();
        }

        ReadOnlyEmeraldo addressBookOptional = XmlFileStorage.loadDataFromSaveFile(new File(filePath));

        return Optional.of(addressBookOptional);
    }

    /**
     * Similar to {@link #saveEmeraldo(ReadOnlyEmeraldo)}
     * @param filePath location of the data. Cannot be null
     */
    public void saveEmeraldo(ReadOnlyEmeraldo addressBook, String filePath) throws IOException {
        assert addressBook != null;
        assert filePath != null;

        File file = new File(filePath);
        FileUtil.createIfMissing(file);
        XmlFileStorage.saveDataToFile(file, new XmlSerializableEmeraldo(addressBook));
    }

    @Override
    public Optional<ReadOnlyEmeraldo> readEmeraldo() throws DataConversionException, IOException {
        return readEmeraldo(filePath);
    }

    @Override
    public void saveEmeraldo(ReadOnlyEmeraldo addressBook) throws IOException {
        saveEmeraldo(addressBook, filePath);
    }
}
