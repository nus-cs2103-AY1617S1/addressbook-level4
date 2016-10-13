package seedu.address.storage;

import seedu.address.commons.core.LogsCenter;
import seedu.address.commons.exceptions.DataConversionException;
import seedu.address.commons.util.FileUtil;
import seedu.address.model.ReadOnlyTaskScheduler;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Optional;
import java.util.logging.Logger;

/**
 * A class to access AddressBook data stored as an xml file on the hard disk.
 */
public class XmlTaskSchedulerStorage implements TaskSchedulerStorage {

    private static final Logger logger = LogsCenter.getLogger(XmlTaskSchedulerStorage.class);

    private String filePath;

    public XmlTaskSchedulerStorage(String filePath){
        this.filePath = filePath;
    }

    public String getTaskSchedulerFilePath(){
        return filePath;
    }

    /**
     * Similar to {@link #readTaskScheduler()}
     * @param filePath location of the data. Cannot be null
     * @throws DataConversionException if the file is not in the correct format.
     */
    public Optional<ReadOnlyTaskScheduler> readAddressBook(String filePath) throws DataConversionException, FileNotFoundException {
        assert filePath != null;

        File addressBookFile = new File(filePath);

        if (!addressBookFile.exists()) {
            logger.info("AddressBook file "  + addressBookFile + " not found");
            return Optional.empty();
        }

        ReadOnlyTaskScheduler addressBookOptional = XmlFileStorage.loadDataFromSaveFile(new File(filePath));

        return Optional.of(addressBookOptional);
    }

    /**
     * Similar to {@link #saveTaskScheduler(ReadOnlyTaskScheduler)}
     * @param filePath location of the data. Cannot be null
     */
    public void saveTaskScheduler(ReadOnlyTaskScheduler addressBook, String filePath) throws IOException {
        assert addressBook != null;
        assert filePath != null;

        File file = new File(filePath);
        FileUtil.createIfMissing(file);
        XmlFileStorage.saveDataToFile(file, new XmlSerializableAddressBook(addressBook));
    }

    @Override
    public Optional<ReadOnlyTaskScheduler> readTaskScheduler() throws DataConversionException, IOException {
        return readAddressBook(filePath);
    }

    @Override
    public void saveTaskScheduler(ReadOnlyTaskScheduler taskScheduler) throws IOException {
        saveTaskScheduler(taskScheduler, filePath);
    }
}
