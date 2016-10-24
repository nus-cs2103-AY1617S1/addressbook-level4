package seedu.cmdo.storage;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Optional;

import seedu.cmdo.commons.events.model.ToDoListChangedEvent;
import seedu.cmdo.commons.events.storage.DataSavingExceptionEvent;
import seedu.cmdo.commons.exceptions.DataConversionException;
import seedu.cmdo.model.ReadOnlyToDoList;
import seedu.cmdo.model.UserPrefs;

/**
 * API of the Storage component
 */
public interface Storage extends ToDoListStorage, UserPrefsStorage {

    @Override
    Optional<UserPrefs> readUserPrefs() throws DataConversionException, IOException;

    @Override
    void saveUserPrefs(UserPrefs userPrefs) throws IOException;

    @Override
    String getToDoListFilePath();

    @Override
    Optional<ReadOnlyToDoList> readToDoList() throws DataConversionException, FileNotFoundException;

    @Override
    void saveToDoList(ReadOnlyToDoList toDoList) throws IOException;

    /**
     * Saves the current version of the Address Book to the hard disk.
     *   Creates the data file if it is missing.
     * Raises {@link DataSavingExceptionEvent} if there was an error during saving.
     */
    void handleToDoListChangedEvent(ToDoListChangedEvent abce);
    
    void updateFilePathInUserPrefs(String filePath) throws DataConversionException, IOException;
}
