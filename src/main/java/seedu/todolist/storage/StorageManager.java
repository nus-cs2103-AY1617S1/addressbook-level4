package seedu.todolist.storage;

import com.google.common.eventbus.Subscribe;

import seedu.todolist.commons.core.ComponentManager;
import seedu.todolist.commons.core.LogsCenter;
import seedu.todolist.commons.events.model.ToDoListChangedEvent;
import seedu.todolist.commons.events.storage.DataSavingExceptionEvent;
import seedu.todolist.commons.exceptions.DataConversionException;
import seedu.todolist.model.ReadOnlyToDoList;
import seedu.todolist.model.UserPrefs;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Optional;
import java.util.logging.Logger;

/**
 * Manages storage of ToDoList data in local storage.
 */
public class StorageManager extends ComponentManager implements Storage {

    private static final Logger logger = LogsCenter.getLogger(StorageManager.class);
    private XmlToDoListStorage ToDoListStorage;
    private JsonUserPrefStorage userPrefStorage;


    public StorageManager(String ToDoListFilePath, String userPrefsFilePath) {
        super();
        this.ToDoListStorage = new XmlToDoListStorage(ToDoListFilePath);
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


    // ================ ToDoList methods ==============================
  
    //@@author A0158963M 
    @Override
    public void setFilePath(String filepath){
    	String todolistFilePath = filepath + "/todolist.xml";
    	ToDoListStorage = new XmlToDoListStorage(todolistFilePath);
    }
    
    @Override
    public String getToDoListFilePath() {
        return ToDoListStorage.getToDoListFilePath();
    }

    @Override
    public Optional<ReadOnlyToDoList> readToDoList() throws DataConversionException, FileNotFoundException {
        logger.fine("Attempting to read data from file: " + ToDoListStorage.getToDoListFilePath());

        return ToDoListStorage.readToDoList(ToDoListStorage.getToDoListFilePath());
    }

    @Override
    public void saveToDoList(ReadOnlyToDoList ToDoList) throws IOException {
        ToDoListStorage.saveToDoList(ToDoList, ToDoListStorage.getToDoListFilePath());
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
