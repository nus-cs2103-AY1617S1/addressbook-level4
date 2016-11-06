package tars.storage;

import tars.commons.exceptions.DataConversionException;
import tars.model.ReadOnlyTars;

import java.io.IOException;
import java.util.Optional;

/**
 * Represents a storage for {@link tars.model.Tars}.
 */
public interface TarsStorage {

    /**
     * Returns the file path of the data file.
     */
    String getTarsFilePath();

    /**
     * Returns Tars data as a {@link ReadOnlyTars}. Returns {@code Optional.empty()} if storage file
     * is not found.
     * 
     * @throws DataConversionException if the data in storage is not in the expected format.
     * @throws IOException if there was any problem when reading from the storage.
     */
    Optional<ReadOnlyTars> readTars() throws DataConversionException, IOException;

    /**
     * Saves the given {@link ReadOnlyTars} to the storage.
     * 
     * @param tars cannot be null.
     * @throws IOException if there was any problem writing to the file.
     */
    void saveTars(ReadOnlyTars tars) throws IOException;

}
