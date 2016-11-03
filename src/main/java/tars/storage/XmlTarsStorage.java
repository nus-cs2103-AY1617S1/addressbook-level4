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

        File tarsFile = new File(filePath);

        if (!tarsFile.exists()) {
            logger.info("Tars file "  + tarsFile + " not found");
            return Optional.empty();
        }

        ReadOnlyTars tarsOptional = XmlFileStorage.loadDataFromSaveFile(new File(filePath));

        return Optional.of(tarsOptional);
    }

    /**
     * Similar to {@link #saveTars(ReadOnlyTars)}
     * @param filePath location of the data. Cannot be null
     */
    public void saveTars(ReadOnlyTars tars, String filePath) throws IOException {
        assert tars != null;
        assert filePath != null;        

        File file = new File(filePath);
        FileUtil.createIfMissing(file);
        XmlFileStorage.saveDataToFile(file, new XmlSerializableTars(tars));
    }

    @Override
    public Optional<ReadOnlyTars> readTars() throws DataConversionException, IOException {
        return readTars(filePath);
    }

    @Override
    public void saveTars(ReadOnlyTars tars) throws IOException {
        saveTars(tars, filePath);
    }
}
