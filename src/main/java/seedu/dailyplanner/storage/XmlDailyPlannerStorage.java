package seedu.dailyplanner.storage;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Optional;
import java.util.logging.Logger;

import seedu.dailyplanner.commons.core.LogsCenter;
import seedu.dailyplanner.commons.exceptions.DataConversionException;
import seedu.dailyplanner.commons.util.FileUtil;
import seedu.dailyplanner.model.ReadOnlyDailyPlanner;

/**
 * A class to access AddressBook data stored as an xml file on the hard disk.
 */
public class XmlDailyPlannerStorage implements DailyPlannerStorage {

    private static final Logger logger = LogsCenter.getLogger(XmlDailyPlannerStorage.class);

    private String filePath;

    public XmlDailyPlannerStorage(String filePath){
        this.filePath = filePath;
    }

    public String getAddressBookFilePath(){
        return filePath;
    }

    /**
     * Similar to {@link #readDailyPlanner()}
     * @param filePath location of the data. Cannot be null
     * @throws DataConversionException if the file is not in the correct format.
     */
    public Optional<ReadOnlyDailyPlanner> readAddressBook(String filePath) throws DataConversionException, FileNotFoundException {
        assert filePath != null;

        File addressBookFile = new File(filePath);

        if (!addressBookFile.exists()) {
            logger.info("AddressBook file "  + addressBookFile + " not found");
            return Optional.empty();
        }

        ReadOnlyDailyPlanner addressBookOptional = XmlFileStorage.loadDataFromSaveFile(new File(filePath));

        return Optional.of(addressBookOptional);
    }

    /**
     * Similar to {@link #saveAddressBook(ReadOnlyDailyPlanner)}
     * @param filePath location of the data. Cannot be null
     */
    public void saveAddressBook(ReadOnlyDailyPlanner addressBook, String filePath) throws IOException {
        assert addressBook != null;
        assert filePath != null;

        File file = new File(filePath);
        FileUtil.createIfMissing(file);
        XmlFileStorage.saveDataToFile(file, new XmlSerializableDailyPlanner(addressBook));
    }

    @Override
    public Optional<ReadOnlyDailyPlanner> readDailyPlanner() throws DataConversionException, IOException {
        return readAddressBook(filePath);
    }

    @Override
    public void saveAddressBook(ReadOnlyDailyPlanner addressBook) throws IOException {
        saveAddressBook(addressBook, filePath);
    }
}
