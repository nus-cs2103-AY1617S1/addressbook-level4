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
 * A class to access Malitio data stored as an xml file on the hard disk.
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

        File malitioFile = new File(filePath);

        if (!malitioFile.exists()) {
            logger.info("malitio file "  + malitioFile + " not found");
            return Optional.empty();
        }

        ReadOnlyMalitio malitioOptional = XmlFileStorage.loadDataFromSaveFile(new File(filePath));

        return Optional.of(malitioOptional);
    }

    /**
     * Similar to {@link #saveMalitio(ReadOnlyMalitio)}
     * @param filePath location of the data. Cannot be null
     */
    public void saveMalitio(ReadOnlyMalitio malitio, String filePath) throws IOException {
        assert malitio != null;
        assert filePath != null;

        File file = new File(filePath);
        FileUtil.createIfMissing(file);
        XmlFileStorage.saveDataToFile(file, new XmlSerializableMalitio(malitio));
    }

    @Override
    public Optional<ReadOnlyMalitio> readMalitio() throws DataConversionException, IOException {
        return readMalitio(filePath);
    }

    @Override
    public void saveMalitio(ReadOnlyMalitio malitio) throws IOException {
        saveMalitio(malitio, filePath);
    }
}
