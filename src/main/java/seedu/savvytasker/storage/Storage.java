package seedu.savvytasker.storage;

import java.io.IOException;
import java.util.Optional;

import seedu.savvytasker.commons.events.model.SavvyTaskerChangedEvent;
import seedu.savvytasker.commons.events.storage.DataSavingExceptionEvent;
import seedu.savvytasker.commons.events.storage.DataSavingLocationChangedEvent;
import seedu.savvytasker.commons.exceptions.DataConversionException;
import seedu.savvytasker.model.ReadOnlySavvyTasker;
import seedu.savvytasker.model.UserPrefs;

/**
 * API of the Storage component
 */
public interface Storage extends SavvyTaskerStorage, UserPrefsStorage {

    @Override
    Optional<UserPrefs> readUserPrefs() throws DataConversionException, IOException;

    @Override
    void saveUserPrefs(UserPrefs userPrefs) throws IOException;

    @Override
    String getSavvyTaskerFilePath();
    
    @Override
    boolean setSavvyTaskerFilePath(String path);

    @Override
    Optional<ReadOnlySavvyTasker> readSavvyTasker() throws DataConversionException, IOException;

    @Override
    void saveSavvyTasker(ReadOnlySavvyTasker savvyTasker) throws IOException;
    
    /**
     * Changes the storage location as requested
     *   Creates the data file at the new location.
     * Raises {@link DataSavingExceptionEvent} if there was an error during saving.
     */
    void handleSavvyTaskerSaveLocationChangedEvent(DataSavingLocationChangedEvent dslce);

    /**
     * Saves the current version of the Savvy Tasker to the hard disk.
     *   Creates the data file if it is missing.
     * Raises {@link DataSavingExceptionEvent} if there was an error during saving.
     */
    void handleSavvyTaskerChangedEvent(SavvyTaskerChangedEvent stce);
}
