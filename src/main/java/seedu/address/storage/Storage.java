package seedu.address.storage;

import seedu.address.commons.events.model.SavvyTaskerChangedEvent;
import seedu.address.commons.events.storage.DataSavingExceptionEvent;
import seedu.address.commons.exceptions.DataConversionException;
import seedu.address.model.ReadOnlySavvyTasker;
import seedu.address.model.UserPrefs;

import java.io.IOException;
import java.util.Optional;

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
