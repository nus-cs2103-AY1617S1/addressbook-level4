package seedu.dailyplanner.storage;

import com.google.common.eventbus.Subscribe;

import seedu.dailyplanner.commons.core.ComponentManager;
import seedu.dailyplanner.commons.core.LogsCenter;
import seedu.dailyplanner.commons.events.model.DailyPlannerChangedEvent;
import seedu.dailyplanner.commons.events.storage.DataSavingExceptionEvent;
import seedu.dailyplanner.commons.exceptions.DataConversionException;
import seedu.dailyplanner.model.ReadOnlyDailyPlanner;
import seedu.dailyplanner.model.UserPrefs;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Optional;
import java.util.logging.Logger;

/**
 * Manages storage of AddressBook data in local storage.
 */
public class StorageManager extends ComponentManager implements Storage {

    private static final Logger logger = LogsCenter.getLogger(StorageManager.class);
    private DailyPlannerStorage dailyPlannerStorage;
    private UserPrefsStorage userPrefsStorage;


    public StorageManager(DailyPlannerStorage dailyPlannerStorage, UserPrefsStorage userPrefsStorage) {
        super();
        this.dailyPlannerStorage = dailyPlannerStorage;
        this.userPrefsStorage = userPrefsStorage;
    }

    public StorageManager(String addressBookFilePath, String userPrefsFilePath) {
        this(new XmlDailyPlannerStorage(addressBookFilePath), new JsonUserPrefsStorage(userPrefsFilePath));
    }

    // ================ UserPrefs methods ==============================

    @Override
    public Optional<UserPrefs> readUserPrefs() throws DataConversionException, IOException {
        return userPrefsStorage.readUserPrefs();
    }

    @Override
    public void saveUserPrefs(UserPrefs userPrefs) throws IOException {
        userPrefsStorage.saveUserPrefs(userPrefs);
    }


    // ================ AddressBook methods ==============================

    @Override
    public String getAddressBookFilePath() {
        return dailyPlannerStorage.getAddressBookFilePath();
    }

    @Override
    public Optional<ReadOnlyDailyPlanner> readDailyPlanner() throws DataConversionException, IOException {
        return readAddressBook(dailyPlannerStorage.getAddressBookFilePath());
    }

    @Override
    public Optional<ReadOnlyDailyPlanner> readAddressBook(String filePath) throws DataConversionException, IOException {
        logger.fine("Attempting to read data from file: " + filePath);
        return dailyPlannerStorage.readAddressBook(filePath);
    }

    @Override
    public void saveAddressBook(ReadOnlyDailyPlanner addressBook) throws IOException {
        saveAddressBook(addressBook, dailyPlannerStorage.getAddressBookFilePath());
    }

    @Override
    public void saveAddressBook(ReadOnlyDailyPlanner addressBook, String filePath) throws IOException {
        logger.fine("Attempting to write to data file: " + filePath);
        dailyPlannerStorage.saveAddressBook(addressBook, filePath);
    }


    @Override
    @Subscribe
    public void handleAddressBookChangedEvent(DailyPlannerChangedEvent event) {
        logger.info(LogsCenter.getEventHandlingLogMessage(event, "Local data changed, saving to file"));
        try {
            saveAddressBook(event.data);
        } catch (IOException e) {
            raise(new DataSavingExceptionEvent(e));
        }
    }

}
