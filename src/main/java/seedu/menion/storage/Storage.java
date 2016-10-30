package seedu.menion.storage;

import seedu.menion.commons.events.model.ActivityManagerChangedEvent;
import seedu.menion.commons.events.model.ActivityManagerChangedEventNoUI;
import seedu.menion.commons.events.storage.DataSavingExceptionEvent;
import seedu.menion.commons.exceptions.DataConversionException;
import seedu.menion.model.ReadOnlyActivityManager;
import seedu.menion.model.UserPrefs;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Optional;

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
    void saveActivityManager(ReadOnlyActivityManager activityManager) throws IOException;

    /**
     * Saves the current version of the Activity Manager to the hard disk.
     *   Creates the data file if it is missing.
     * Raises {@link DataSavingExceptionEvent} if there was an error during saving.
     */
    void handleActivityManagerChangedEvent(ActivityManagerChangedEvent abce);
    
    /**
     * Saves the current version of the Activity Manager to the hard disk.
     *   Creates the data file if it is missing.
     * Raises {@link DataSavingExceptionEvent} if there was an error during saving.
     */
    void handleActivityManagerChangedEventNoUI(ActivityManagerChangedEventNoUI abce);
}
