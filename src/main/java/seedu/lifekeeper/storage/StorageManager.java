package seedu.lifekeeper.storage;

import com.google.common.eventbus.Subscribe;

import seedu.lifekeeper.commons.core.ComponentManager;
import seedu.lifekeeper.commons.core.EventsCenter;
import seedu.lifekeeper.commons.core.LogsCenter;
import seedu.lifekeeper.commons.events.model.LifekeeperChangedEvent;
import seedu.lifekeeper.commons.events.model.LoadLifekeeperEvent;
import seedu.lifekeeper.commons.events.storage.DataSavingExceptionEvent;
import seedu.lifekeeper.commons.events.ui.FileDirectoryChangedEvent;
import seedu.lifekeeper.commons.exceptions.DataConversionException;
import seedu.lifekeeper.model.ReadOnlyLifeKeeper;
import seedu.lifekeeper.model.UserPrefs;

import java.io.IOException;
import java.util.Optional;
import java.util.logging.Logger;

/**
 * Manages storage of AddressBook data in local storage.
 */
public class StorageManager extends ComponentManager implements Storage {

    private static final Logger logger = LogsCenter.getLogger(StorageManager.class);
    private static AddressBookStorage addressBookStorage;
    private static UserPrefsStorage userPrefsStorage;


    public StorageManager(AddressBookStorage addressBookStorage, UserPrefsStorage userPrefsStorage) {
        super();
        this.addressBookStorage = addressBookStorage;
        this.userPrefsStorage = userPrefsStorage;
    }

    public StorageManager(String addressBookFilePath, String userPrefsFilePath) {
        this(new XmlAddressBookStorage(addressBookFilePath), new JsonUserPrefsStorage(userPrefsFilePath));
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
        return addressBookStorage.getAddressBookFilePath();
    }
    
    @Override
    public void setAddressBookFilePath(String addressBookFilePath) {
        this.addressBookStorage = new XmlAddressBookStorage(addressBookFilePath);
    }

    @Override
    public Optional<ReadOnlyLifeKeeper> readAddressBook() throws DataConversionException, IOException {
        return readAddressBook(addressBookStorage.getAddressBookFilePath());
    }

    @Override
    public Optional<ReadOnlyLifeKeeper> readAddressBook(String filePath) throws DataConversionException, IOException {
        logger.fine("Attempting to read data from file: " + filePath);
        return addressBookStorage.readAddressBook(filePath);
    }

    @Override
    public void saveAddressBook(ReadOnlyLifeKeeper lifeKeeper) throws IOException {
        saveAddressBook(lifeKeeper, addressBookStorage.getAddressBookFilePath());
    }

    @Override
    public void saveAddressBook(ReadOnlyLifeKeeper lifeKeeper, String filePath) throws IOException {
        logger.fine("Attempting to write to data file: " + filePath);
        addressBookStorage.saveAddressBook(lifeKeeper, filePath);
    }


    @Override
    @Subscribe
    public void handleAddressBookChangedEvent(LifekeeperChangedEvent event) {
        logger.info(LogsCenter.getEventHandlingLogMessage(event, "Local data changed, saving to file"));
        try {
            saveAddressBook(event.data);
        } catch (IOException e) {
            raise(new DataSavingExceptionEvent(e));
        }
    }
    
    @Override
    @Subscribe
    public void handleLoadLifekeeperEvent(LoadLifekeeperEvent event) throws DataConversionException {
        logger.info(LogsCenter.getEventHandlingLogMessage(event));
        
        Optional<ReadOnlyLifeKeeper> lifekeeperOptional;
        try {
            lifekeeperOptional = readAddressBook(event.openFile.getAbsolutePath());
            
            if (lifekeeperOptional.isPresent()) {
                EventsCenter.getInstance().post(new FileDirectoryChangedEvent(event.openFile.getAbsolutePath()));
                event.logic.resetData(lifekeeperOptional.get());
            }
        } catch (DataConversionException e) {
            logger.warning("Data file not in the correct format. Loading aborted.");
        } catch (IOException e) {
            raise(new DataSavingExceptionEvent(e));
        }
    }

}
