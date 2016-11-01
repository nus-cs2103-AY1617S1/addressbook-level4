package seedu.agendum.storage;

import seedu.agendum.commons.events.model.LoadDataRequestEvent;
import seedu.agendum.commons.events.logic.AliasTableChangedEvent;
import seedu.agendum.commons.events.model.ChangeSaveLocationEvent;
import seedu.agendum.commons.events.model.ToDoListChangedEvent;
import seedu.agendum.commons.events.storage.DataSavingExceptionEvent;
import seedu.agendum.commons.exceptions.DataConversionException;
import seedu.agendum.model.ReadOnlyToDoList;
import seedu.agendum.model.UserPrefs;

import java.io.IOException;
import java.util.Hashtable;
import java.util.Optional;

/**
 * API of the Storage component
 */
public interface Storage extends ToDoListStorage, UserPrefsStorage, AliasTableStorage {

    @Override
    Optional<Hashtable<String, String>> readAliasTable()
            throws DataConversionException, IOException;

    @Override
    void saveAliasTable(Hashtable<String, String> table) throws IOException;

    @Override
    Optional<UserPrefs> readUserPrefs() throws DataConversionException, IOException;

    @Override
    void saveUserPrefs(UserPrefs userPrefs) throws IOException;

    @Override
    String getToDoListFilePath();
    
    @Override
    void setToDoListFilePath(String filePath);

    @Override
    Optional<ReadOnlyToDoList> readToDoList() throws DataConversionException, IOException;

    @Override
    void saveToDoList(ReadOnlyToDoList toDoList) throws IOException;

    /**
     * Saves the current version of the To Do List to the hard disk.
     *   Creates the data file if it is missing.
     * Raises {@link DataSavingExceptionEvent} if there was an error during saving.
     */
    void handleToDoListChangedEvent(ToDoListChangedEvent event);

    /**
     * Saves the current version of the alias table in Command Library to the hard disk.
     *   Creates the data file if it is missing.
     * Raises {@link DataSavingExceptionEvent} if there was an error during saving.
     */
    void handleAliasTableChangedEvent(AliasTableChangedEvent event);

    /** Loads todo list data from the file **/
    void handleLoadDataRequestEvent(LoadDataRequestEvent event);
    
    /** Sets the save location **/
    void handleChangeSaveLocationEvent(ChangeSaveLocationEvent event);
}
