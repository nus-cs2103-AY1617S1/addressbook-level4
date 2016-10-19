package seedu.jimi.storage;

import java.io.IOException;
import java.util.Optional;

import seedu.jimi.commons.exceptions.DataConversionException;
import seedu.jimi.model.ReadOnlyTaskBook;

/**
 * Represents a storage for {@link seedu.jimi.model.TaskBook}.
 */
public interface TaskBookStorage {

    /**
     * Returns the file path of the data file.
     */
    String getTaskBookFilePath();

    /**
     * Returns TaskBook data as a {@link ReadOnlyTaskBook}.
     *   Returns {@code Optional.empty()} if storage file is not found.
     * @throws DataConversionException if the data in storage is not in the expected format.
     * @throws IOException if there was any problem when reading from the storage.
     */
    Optional<ReadOnlyTaskBook> readTaskBook() throws DataConversionException, IOException;

    /**
     * @see #getTaskBookFilePath()
     */
    Optional<ReadOnlyTaskBook> readTaskBook(String filePath) throws DataConversionException, IOException;

    /**
     * Saves the given {@link ReadOnlyTaskBook} to the storage.
     * @param taskBook cannot be null.
     * @throws IOException if there was any problem writing to the file.
     */
    void saveTaskBook(ReadOnlyTaskBook taskBook) throws IOException;

    /**
     * @see #saveTaskBook(ReadOnlyTaskBook)
     */
    void saveTaskBook(ReadOnlyTaskBook taskBook, String filePath) throws IOException;

}
