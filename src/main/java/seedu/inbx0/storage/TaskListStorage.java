package seedu.inbx0.storage;

import java.io.IOException;
import java.util.Optional;

import seedu.inbx0.commons.exceptions.DataConversionException;
import seedu.inbx0.model.ReadOnlyTaskList;

/**
 * Represents a storage for {@link seedu.inbx0.model.TaskList}.
 */
public interface TaskListStorage {

    /**
     * Returns the file path of the data file.
     */
    String getAddressBookFilePath();

    /**
     * Returns TaskList data as a {@link ReadOnlyTaskList}.
     *   Returns {@code Optional.empty()} if storage file is not found.
     * @throws DataConversionException if the data in storage is not in the expected format.
     * @throws IOException if there was any problem when reading from the storage.
     */
    Optional<ReadOnlyTaskList> readAddressBook() throws DataConversionException, IOException;

    /**
     * @see #getAddressBookFilePath()
     */
    Optional<ReadOnlyTaskList> readAddressBook(String filePath) throws DataConversionException, IOException;

    /**
     * Saves the given {@link ReadOnlyTaskList} to the storage.
     * @param addressBook cannot be null.
     * @throws IOException if there was any problem writing to the file.
     */
    void saveAddressBook(ReadOnlyTaskList addressBook) throws IOException;

    /**
     * @see #saveAddressBook(ReadOnlyTaskList)
     */
    void saveAddressBook(ReadOnlyTaskList addressBook, String filePath) throws IOException;

}
