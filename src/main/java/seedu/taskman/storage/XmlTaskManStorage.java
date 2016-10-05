package seedu.taskman.storage;

import seedu.taskman.commons.core.LogsCenter;
import seedu.taskman.commons.exceptions.DataConversionException;
import seedu.taskman.commons.util.FileUtil;
import seedu.taskman.model.ReadOnlyTaskMan;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Optional;
import java.util.logging.Logger;

/**
 * A class to access TaskMan data stored as an xml file on the hard disk.
 */
public class XmlTaskManStorage implements TaskManStorage {

    private static final Logger logger = LogsCenter.getLogger(XmlTaskManStorage.class);

    private String filePath;

    public XmlTaskManStorage(String filePath){
        this.filePath = filePath;
    }

    public String getTaskManFilePath(){
        return filePath;
    }

    /**
     * Similar to {@link #readTaskMan()}
     * @param filePath location of the data. Cannot be null
     * @throws DataConversionException if the file is not in the correct format.
     */
    public Optional<ReadOnlyTaskMan> readTaskMan(String filePath) throws DataConversionException, FileNotFoundException {
        assert filePath != null;

        File taskManFile = new File(filePath);

        if (!taskManFile.exists()) {
            logger.info("TaskMan file "  + taskManFile + " not found");
            return Optional.empty();
        }

        ReadOnlyTaskMan taskManOptional = XmlFileStorage.loadDataFromSaveFile(new File(filePath));

        return Optional.of(taskManOptional);
    }

    /**
     * Similar to {@link #saveTaskMan(ReadOnlyTaskMan)}
     * @param filePath location of the data. Cannot be null
     */
    public void saveTaskMan(ReadOnlyTaskMan taskMan, String filePath) throws IOException {
        assert taskMan != null;
        assert filePath != null;

        File file = new File(filePath);
        FileUtil.createIfMissing(file);
        XmlFileStorage.saveDataToFile(file, new XmlSerializableTaskMan(taskMan));
    }

    @Override
    public Optional<ReadOnlyTaskMan> readTaskMan() throws DataConversionException, IOException {
        return readTaskMan(filePath);
    }

    @Override
    public void saveTaskMan(ReadOnlyTaskMan taskMan) throws IOException {
        saveTaskMan(taskMan, filePath);
    }
}
