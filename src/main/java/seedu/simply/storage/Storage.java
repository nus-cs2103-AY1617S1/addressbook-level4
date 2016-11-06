package seedu.simply.storage;

import seedu.simply.commons.events.model.TaskBookChangedEvent;
import seedu.simply.commons.events.storage.DataSavingExceptionEvent;
import seedu.simply.commons.exceptions.DataConversionException;
import seedu.simply.model.ReadOnlyTaskBook;
import seedu.simply.model.UserPrefs;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Optional;

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
     * Saves the current version of the Task Book to the hard disk.
     *   Creates the data file if it is missing.
     * Raises {@link DataSavingExceptionEvent} if there was an error during saving.
     */
    void handleTaskBookChangedEvent(TaskBookChangedEvent abce);
}
