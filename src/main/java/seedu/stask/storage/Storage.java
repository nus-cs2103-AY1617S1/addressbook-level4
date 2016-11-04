package seedu.stask.storage;

import java.io.IOException;
import java.util.Optional;

import seedu.stask.commons.core.LogsCenter;
import seedu.stask.commons.events.model.TaskBookChangedEvent;
import seedu.stask.commons.events.storage.DataSavingExceptionEvent;
import seedu.stask.commons.events.storage.StorageDataPathChangedEvent;
import seedu.stask.commons.exceptions.DataConversionException;
import seedu.stask.model.ReadOnlyTaskBook;
import seedu.stask.model.UserPrefs;

/**
 * API of the Storage component
 */
public interface Storage extends TaskBookStorage, UserPrefsStorage {

    @Override
    Optional<UserPrefs> readUserPrefs() throws DataConversionException, IOException;

    @Override
    void saveUserPrefs(UserPrefs userPrefs) throws IOException;

    @Override
    String getTaskBookFilePath();

    @Override
    Optional<ReadOnlyTaskBook> readTaskBook() throws DataConversionException, IOException;

    @Override
    void saveTaskBook(ReadOnlyTaskBook taskBook) throws IOException;

    /**
     * Saves the current version of the Address Book to the hard disk.
     *   Creates the data file if it is missing.
     * Raises {@link DataSavingExceptionEvent} if there was an error during saving.
     */
    void handleTaskBookChangedEvent(TaskBookChangedEvent abce);
    
    //@@author A0139528W
    /**
     * Saves the new path of TaskBook to the hard disk
     */
    void handleStorageDataChangedEvent(StorageDataPathChangedEvent sdce);
    //@@author
}
