package seedu.manager.storage;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Optional;

import seedu.manager.commons.events.model.ActivityManagerChangedEvent;
import seedu.manager.commons.events.storage.DataSavingExceptionEvent;
import seedu.manager.commons.exceptions.DataConversionException;
import seedu.manager.model.ReadOnlyActivityManager;
import seedu.manager.model.UserPrefs;

/**
 * API of the Storage component
 */
public interface Storage extends ActivityManagerStorage, UserPrefsStorage {

    @Override
    Optional<UserPrefs> readUserPrefs() throws DataConversionException, IOException;

    @Override
    void saveUserPrefs(UserPrefs userPrefs) throws IOException;

    @Override
    String getActivityManagerFilePath();

    @Override
    Optional<ReadOnlyActivityManager> readActivityManager() throws DataConversionException, FileNotFoundException;

    @Override
    void saveActivityManager(ReadOnlyActivityManager addressBook) throws IOException;

    /**
     * Saves the current version of the Address Book to the hard disk.
     *   Creates the data file if it is missing.
     * Raises {@link DataSavingExceptionEvent} if there was an error during saving.
     */
    void handleActivityManagerChangedEvent(ActivityManagerChangedEvent abce);
}
