package seedu.smartscheduler.storage;

import java.io.IOException;
import java.util.Optional;

import seedu.smartscheduler.commons.exceptions.DataConversionException;
import seedu.smartscheduler.model.ReadOnlyTaskList;

/**
 * Represents a storage for {@link seedu.smartscheduler.model.TaskList}.
 */
public interface TaskListStorage {

    /**
     * Returns the file path of the data file.
     */
    String getAddressBookFilePath();

    /**
     * Returns AddressBook data as a {@link ReadOnlyTaskList}.
     *   Returns {@code Optional.empty()} if storage file is not found.
     * @throws DataConversionException if the data in storage is not in the expected format.
     * @throws IOException if there was any problem when reading from the storage.
     */
    Optional<ReadOnlyTaskList> readAddressBook() throws DataConversionException, IOException;

    /**
     * Saves the given {@link ReadOnlyTaskList} to the storage.
     * @param addressBook cannot be null.
     * @throws IOException if there was any problem writing to the file.
     */
    void saveAddressBook(ReadOnlyTaskList addressBook) throws IOException;

}
