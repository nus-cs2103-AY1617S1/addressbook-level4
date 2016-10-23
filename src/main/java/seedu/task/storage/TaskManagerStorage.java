package seedu.task.storage;

import java.io.IOException;
import java.util.Optional;

import seedu.task.commons.exceptions.DataConversionException;
import seedu.task.model.ReadOnlyTaskManager;

/**
 * Represents a storage for {@link seedu.task.model.TaskManager}.
 */
public interface TaskManagerStorage {

    /**
     * Returns the file path of the data file.
     */
    String getAddressBookFilePath();

    /**
     * Returns AddressBook data as a {@link ReadOnlyTaskManager}.
     *   Returns {@code Optional.empty()} if storage file is not found.
     * @throws DataConversionException if the data in storage is not in the expected format.
     * @throws IOException if there was any problem when reading from the storage.
     */
    Optional<ReadOnlyTaskManager> readAddressBook() throws DataConversionException, IOException;

    /**
     * @see #getAddressBookFilePath()
     */
    Optional<ReadOnlyTaskManager> readAddressBook(String filePath) throws DataConversionException, IOException;

    /**
     * Saves the given {@link ReadOnlyTaskManager} to the storage.
     * @param addressBook cannot be null.
     * @throws IOException if there was any problem writing to the file.
     */
    void saveAddressBook(ReadOnlyTaskManager addressBook) throws IOException;

    /**
     * @see #saveAddressBook(ReadOnlyTaskManager)
     */
    void saveAddressBook(ReadOnlyTaskManager addressBook, String filePath) throws IOException;

}
