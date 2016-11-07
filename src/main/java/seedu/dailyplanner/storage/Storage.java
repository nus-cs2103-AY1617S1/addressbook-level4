package seedu.dailyplanner.storage;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Optional;

import seedu.dailyplanner.commons.events.model.DailyPlannerChangedEvent;
import seedu.dailyplanner.commons.events.storage.DataSavingExceptionEvent;
import seedu.dailyplanner.commons.exceptions.DataConversionException;
import seedu.dailyplanner.model.ReadOnlyDailyPlanner;
import seedu.dailyplanner.model.UserPrefs;

/**
 * API of the Storage component
 */
public interface Storage extends DailyPlannerStorage, UserPrefsStorage {

    @Override
    Optional<UserPrefs> readUserPrefs() throws DataConversionException, IOException;

    @Override
    void saveUserPrefs(UserPrefs userPrefs) throws IOException;

    @Override
    String getAddressBookFilePath();

    @Override
    Optional<ReadOnlyDailyPlanner> readDailyPlanner() throws DataConversionException, IOException;

    @Override
    void saveAddressBook(ReadOnlyDailyPlanner addressBook) throws IOException;

    /**
     * Saves the current version of the Address Book to the hard disk.
     *   Creates the data file if it is missing.
     * Raises {@link DataSavingExceptionEvent} if there was an error during saving.
     */
    void handleAddressBookChangedEvent(DailyPlannerChangedEvent abce);
}
