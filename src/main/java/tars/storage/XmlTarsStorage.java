package tars.storage;

import tars.commons.core.LogsCenter;
import tars.commons.exceptions.DataConversionException;
import tars.commons.util.FileUtil;
import tars.model.ReadOnlyTars;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Optional;
import java.util.logging.Logger;

/**
 * A class to access Tars data stored as an xml file on the hard disk.
 */
public class XmlTarsStorage implements TarsStorage {

    private static final Logger logger = LogsCenter.getLogger(XmlTarsStorage.class);

    private String filePath;

    public XmlTarsStorage(String filePath){
        this.filePath = filePath;
    }

    public String getTarsFilePath(){
        return filePath;
    }

    /**
     * Similar to {@link #readTars()}
     * @param filePath location of the data. Cannot be null
     * @throws DataConversionException if the file is not in the correct format.
     */
    public Optional<ReadOnlyTars> readTars(String filePath) throws DataConversionException, FileNotFoundException {
        assert filePath != null;

        File addressBookFile = new File(filePath);

        if (!addressBookFile.exists()) {
            logger.info("Tars file "  + addressBookFile + " not found");
            return Optional.empty();
        }

        ReadOnlyTars addressBookOptional = XmlFileStorage.loadDataFromSaveFile(new File(filePath));

        return Optional.of(addressBookOptional);
    }

    /**
     * Similar to {@link #saveTars(ReadOnlyTars)}
     * @param filePath location of the data. Cannot be null
     */
    public void saveTars(ReadOnlyTars addressBook, String filePath) throws IOException {
        assert addressBook != null;
        assert filePath != null;

        File file = new File(filePath);
        FileUtil.createIfMissing(file);
        XmlFileStorage.saveDataToFile(file, new XmlSerializableTars(addressBook));
    }

    @Override
    public Optional<ReadOnlyTars> readTars() throws DataConversionException, IOException {
        return readTars(filePath);
    }

    @Override
    public void saveTars(ReadOnlyTars addressBook) throws IOException {
        saveTars(addressBook, filePath);
    }
}
