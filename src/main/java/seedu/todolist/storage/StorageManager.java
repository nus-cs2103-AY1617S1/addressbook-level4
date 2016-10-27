package seedu.todolist.storage;

import com.google.common.eventbus.Subscribe;

import seedu.todolist.commons.core.ComponentManager;
import seedu.todolist.commons.core.LogsCenter;
import seedu.todolist.commons.events.model.AddressBookChangedEvent;
import seedu.todolist.commons.events.storage.DataSavingExceptionEvent;
import seedu.todolist.commons.exceptions.DataConversionException;
import seedu.todolist.model.ReadOnlyAddressBook;
import seedu.todolist.model.UserPrefs;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Optional;
import java.util.logging.Logger;

/**
 * Manages storage of AddressBook data in local storage.
 */
public class StorageManager extends ComponentManager implements Storage {

    private static final Logger logger = LogsCenter.getLogger(StorageManager.class);
    private XmlAddressBookStorage addressBookStorage;
    private JsonUserPrefStorage userPrefStorage;


    public StorageManager(String addressBookFilePath, String userPrefsFilePath) {
        super();
        this.addressBookStorage = new XmlAddressBookStorage(addressBookFilePath);
        this.userPrefStorage = new JsonUserPrefStorage(userPrefsFilePath);
    }

    // ================ UserPrefs methods ==============================

    @Override
    public Optional<UserPrefs> readUserPrefs() throws DataConversionException, IOException {
        return userPrefStorage.readUserPrefs();
    }

    @Override
    public void saveUserPrefs(UserPrefs userPrefs) throws IOException {
        userPrefStorage.saveUserPrefs(userPrefs);
    }


    // ================ AddressBook methods ==============================

    @Override
    public void setFilePath(String filepath){
    	String todolistFilePath = filepath + "/tolist.xml";
    	addressBookStorage = new XmlAddressBookStorage(todolistFilePath);
    }
    
    @Override
    public String getAddressBookFilePath() {
        return addressBookStorage.getAddressBookFilePath();
    }

    @Override
    public Optional<ReadOnlyAddressBook> readAddressBook() throws DataConversionException, FileNotFoundException {
        logger.fine("Attempting to read data from file: " + addressBookStorage.getAddressBookFilePath());

        return addressBookStorage.readAddressBook(addressBookStorage.getAddressBookFilePath());
    }

    @Override
    public void saveAddressBook(ReadOnlyAddressBook addressBook) throws IOException {
        addressBookStorage.saveAddressBook(addressBook, addressBookStorage.getAddressBookFilePath());
    }


    @Override
    @Subscribe
    public void handleAddressBookChangedEvent(AddressBookChangedEvent event) {
        logger.info(LogsCenter.getEventHandlingLogMessage(event, "Local data changed, saving to file"));
        try {
            saveAddressBook(event.data);
        } catch (IOException e) {
            raise(new DataSavingExceptionEvent(e));
        }
    }

}
