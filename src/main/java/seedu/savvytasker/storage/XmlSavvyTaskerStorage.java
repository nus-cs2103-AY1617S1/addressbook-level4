package seedu.savvytasker.storage;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Optional;
import java.util.logging.Logger;

import seedu.savvytasker.commons.core.LogsCenter;
import seedu.savvytasker.commons.exceptions.DataConversionException;
import seedu.savvytasker.commons.util.FileUtil;
import seedu.savvytasker.model.ReadOnlySavvyTasker;

/**
 * A class to access SavvyTasker data stored as an xml file on the hard disk.
 */
public class XmlSavvyTaskerStorage implements SavvyTaskerStorage {

    private static final Logger logger = LogsCenter.getLogger(XmlSavvyTaskerStorage.class);

    private String filePath;

    public XmlSavvyTaskerStorage(String filePath){
        this.filePath = filePath;
    }

    @Override
    public String getSavvyTaskerFilePath() {
        return filePath;
    }

    /**
     * Similar to {@link #readSavvyTasker()}
     * @param filePath location of the data. Cannot be null
     * @throws DataConversionException if the file is not in the correct format.
     */
    public Optional<ReadOnlySavvyTasker> readSavvyTasker(String filePath) throws DataConversionException, FileNotFoundException {
        assert filePath != null;

        File savvyTaskerFile = new File(filePath);

        if (!savvyTaskerFile.exists()) {
            logger.info("SavvyTasker file "  + savvyTaskerFile + " not found");
            return Optional.empty();
        }

        ReadOnlySavvyTasker savvyTaskerOptional = XmlFileStorage.loadDataFromSaveFile(new File(filePath));

        return Optional.of(savvyTaskerOptional);
    }

    /**
     * Similar to {@link #saveSavvyTasker(ReadOnlySavvyTasker)}
     * @param filePath location of the data. Cannot be null
     */
    public void saveSavvyTasker(ReadOnlySavvyTasker savvyTasker, String filePath) throws IOException {
        assert savvyTasker != null;
        assert filePath != null;

        File file = new File(filePath);
        FileUtil.createIfMissing(file);
        XmlFileStorage.saveDataToFile(file, new XmlSerializableSavvyTasker(savvyTasker));
    }

    @Override
    public Optional<ReadOnlySavvyTasker> readSavvyTasker() throws DataConversionException, IOException {
        return readSavvyTasker(filePath);
    }

    @Override
    public void saveSavvyTasker(ReadOnlySavvyTasker savvyTasker) throws IOException {
        saveSavvyTasker(savvyTasker, filePath);
    }
}
