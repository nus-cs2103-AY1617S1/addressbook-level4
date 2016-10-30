package seedu.emeraldo.storage;

import seedu.emeraldo.commons.core.LogsCenter;
import seedu.emeraldo.commons.exceptions.DataConversionException;
import seedu.emeraldo.commons.util.FileUtil;
import seedu.emeraldo.model.ReadOnlyEmeraldo;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Optional;
import java.util.logging.Logger;

/**
 * A class to access Emeraldo data stored as an xml file on the hard disk.
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
    
    public void changeEmeraldoFilePath(String filepath){
        this.filePath = filepath;
    }

    /**
     * Similar to {@link #readEmeraldo()}
     * @param filePath location of the data. Cannot be null
     * @throws DataConversionException if the file is not in the correct format.
     */
    public Optional<ReadOnlyEmeraldo> readEmeraldo(String filePath) throws DataConversionException, FileNotFoundException {
        assert filePath != null;

        File emeraldoFile = new File(filePath);

        if (!emeraldoFile.exists()) {
            logger.info("Emeraldofile "  + emeraldoFile + " not found");
            return Optional.empty();
        }

        ReadOnlyEmeraldo emeraldoOptional = XmlFileStorage.loadDataFromSaveFile(new File(filePath));

        return Optional.of(emeraldoOptional);
    }

    /**
     * Similar to {@link #saveEmeraldo(ReadOnlyEmeraldo)}
     * @param filePath location of the data. Cannot be null
     */
    public void saveEmeraldo(ReadOnlyEmeraldo emeraldo, String filePath) throws IOException {
        assert emeraldo != null;
        assert filePath != null;

        File file = new File(filePath);
        FileUtil.createIfMissing(file);
        XmlFileStorage.saveDataToFile(file, new XmlSerializableEmeraldo(emeraldo));
    }

    @Override
    public Optional<ReadOnlyEmeraldo> readEmeraldo() throws DataConversionException, IOException {
        return readEmeraldo(filePath);
    }

    @Override
    public void saveEmeraldo(ReadOnlyEmeraldo emeraldo) throws IOException {
        saveEmeraldo(emeraldo, filePath);
    }
}
