package seedu.dailyplanner.storage;

import java.io.IOException;
import java.util.Optional;

import seedu.dailyplanner.commons.exceptions.DataConversionException;
import seedu.dailyplanner.model.ReadOnlyDailyPlanner;

/**
 * Represents a storage for {@link seedu.dailyplanner.model.DailyPlanner}.
 */
public interface DailyPlannerStorage {

    /**
     * Returns the file path of the data file.
     */
    String getAddressBookFilePath();

    /**
     * Returns AddressBook data as a {@link ReadOnlyDailyPlanner}.
     *   Returns {@code Optional.empty()} if storage file is not found.
     * @throws DataConversionException if the data in storage is not in the expected format.
     * @throws IOException if there was any problem when reading from the storage.
     */
    Optional<ReadOnlyDailyPlanner> readAddressBook() throws DataConversionException, IOException;

    /**
     * @see #getAddressBookFilePath()
     */
    Optional<ReadOnlyDailyPlanner> readAddressBook(String filePath) throws DataConversionException, IOException;

    /**
     * Saves the given {@link ReadOnlyDailyPlanner} to the storage.
     * @param addressBook cannot be null.
     * @throws IOException if there was any problem writing to the file.
     */
    void saveAddressBook(ReadOnlyDailyPlanner addressBook) throws IOException;

    /**
     * @see #saveAddressBook(ReadOnlyDailyPlanner)
     */
    void saveAddressBook(ReadOnlyDailyPlanner addressBook, String filePath) throws IOException;

}
