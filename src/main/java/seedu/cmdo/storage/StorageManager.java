package seedu.cmdo.storage;

import com.google.common.eventbus.Subscribe;

import seedu.cmdo.commons.core.ComponentManager;
import seedu.cmdo.commons.core.LogsCenter;
import seedu.cmdo.commons.events.model.ToDoListChangedEvent;
import seedu.cmdo.commons.events.storage.DataSavingExceptionEvent;
import seedu.cmdo.commons.exceptions.DataConversionException;
import seedu.cmdo.model.ReadOnlyToDoList;
import seedu.cmdo.model.UserPrefs;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Optional;
import java.util.logging.Logger;

/**
 * Manages storage of ToDoList data in local storage.
 */
public class StorageManager extends ComponentManager implements Storage {

    private static final Logger logger = LogsCenter.getLogger(StorageManager.class);
    private XmlToDoListStorage toDoListStorage;
    private JsonUserPrefStorage userPrefStorage;


    public StorageManager(String toDoListFilePath, String userPrefsFilePath) {
        super();
        this.toDoListStorage = new XmlToDoListStorage(toDoListFilePath);
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
        
    /**
     * Updates the user prefs file with filePath change.
     * 
     * @param filePath
     * @throws DataConversionException
     * @throws IOException
     *
     * @@author A0139661Y
     */
    @Override
    public void updateFilePathInUserPrefs(String filePath) throws DataConversionException, IOException {
    	Optional<UserPrefs> prefsOptional = userPrefStorage.readUserPrefs();
    	UserPrefs userPrefs = prefsOptional.orElse(new UserPrefs());
    	userPrefs.setStorageSettings(filePath);
    	userPrefStorage.saveUserPrefs(userPrefs);
    	updateFilePathInXmlToDoListStorage(filePath);
    }
    
    private void updateFilePathInXmlToDoListStorage(String filePath) {
    	this.toDoListStorage.setToDoListFilePath(filePath);
    }

    // ================ ToDoList methods ==============================

    @Override
    public String getToDoListFilePath() {
        return toDoListStorage.getToDoListFilePath();
    }

    @Override
    public Optional<ReadOnlyToDoList> readToDoList() throws DataConversionException, FileNotFoundException {
        logger.fine("Attempting to read data from file: " + toDoListStorage.getToDoListFilePath());

        return toDoListStorage.readToDoList(toDoListStorage.getToDoListFilePath());
    }

    @Override
    public void saveToDoList(ReadOnlyToDoList toDoList) throws IOException {
        toDoListStorage.saveToDoList(toDoList, toDoListStorage.getToDoListFilePath());
    }


    @Override
    @Subscribe
    public void handleToDoListChangedEvent(ToDoListChangedEvent event) {
        logger.info(LogsCenter.getEventHandlingLogMessage(event, "Local data changed, saving to file"));
        try {
            saveToDoList(event.data);
        } catch (IOException e) {
            raise(new DataSavingExceptionEvent(e));
        }
    }

}
