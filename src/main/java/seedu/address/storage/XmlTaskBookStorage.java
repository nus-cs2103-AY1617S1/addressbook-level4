package seedu.address.storage;

import seedu.address.commons.core.LogsCenter;
import seedu.address.commons.exceptions.DataConversionException;
import seedu.address.commons.util.FileUtil;
import seedu.address.model.ReadOnlyTaskBook;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Optional;
import java.util.logging.Logger;

/**
 * A class to access TaskBook data stored as an xml file on the hard disk.
 */
public class XmlTaskBookStorage implements TaskBookStorage {

    private static final Logger logger = LogsCenter.getLogger(XmlTaskBookStorage.class);

    private String filePath;

    public XmlTaskBookStorage(String filePath){
        this.filePath = filePath;
    }

    public String getTaskBookFilePath(){
        return filePath;
    }

    /**
     * Similar to {@link #readTaskBook()}
     * @param filePath location of the data. Cannot be null
     * @throws DataConversionException if the file is not in the correct format.
     */
    public Optional<ReadOnlyTaskBook> readTaskBook(String filePath) throws DataConversionException, FileNotFoundException {
        assert filePath != null;

        File taskBookFile = new File(filePath);

        if (!taskBookFile.exists()) {
            logger.info("TaskBook file "  + taskBookFile + " not found");
            return Optional.empty();
        }

        ReadOnlyTaskBook taskBookOptional = XmlFileStorage.loadDataFromSaveFile(new File(filePath));

        return Optional.of(taskBookOptional);
    }

    /**
     * Similar to {@link #saveTaskBook(ReadOnlyTaskBook)}
     * @param filePath location of the data. Cannot be null
     */
    public void saveTaskBook(ReadOnlyTaskBook taskBook, String filePath) throws IOException {
        assert taskBook != null;
        assert filePath != null;

        File file = new File(filePath);
        FileUtil.createIfMissing(file);
        XmlFileStorage.saveDataToFile(file, new XmlSerializableTaskBook(taskBook));
    }

    @Override
    public Optional<ReadOnlyTaskBook> readTaskBook() throws DataConversionException, IOException {
        return readTaskBook(filePath);
    }

    @Override
    public void saveTaskBook(ReadOnlyTaskBook taskBook) throws IOException {
        saveTaskBook(taskBook, filePath);
    }
}
