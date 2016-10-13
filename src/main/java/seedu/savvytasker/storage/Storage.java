package seedu.savvytasker.storage;

import java.io.IOException;
import java.util.Optional;

import seedu.savvytasker.commons.events.model.SavvyTaskerChangedEvent;
import seedu.savvytasker.commons.events.storage.DataSavingExceptionEvent;
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
    Optional<ReadOnlySavvyTasker> readSavvyTasker() throws DataConversionException, IOException;

    @Override
    void saveSavvyTasker(ReadOnlySavvyTasker savvyTasker) throws IOException;

    /**
     * Saves the current version of the Address Book to the hard disk.
     *   Creates the data file if it is missing.
     * Raises {@link DataSavingExceptionEvent} if there was an error during saving.
     */
    void handleSavvyTaskerChangedEvent(SavvyTaskerChangedEvent stce);
}
