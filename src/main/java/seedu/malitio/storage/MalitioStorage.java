package seedu.malitio.storage;

import seedu.malitio.commons.exceptions.DataConversionException;
import seedu.malitio.model.ReadOnlyMalitio;

import java.io.IOException;
import java.util.Optional;

/**
 * Represents a storage for {@link seedu.malitio.model.Malitio}.
 */
public interface MalitioStorage {

    /**
     * Returns the file path of the data file.
     */
    String getMalitioFilePath();

    /**
     * Returns malitio data as a {@link ReadOnlyMalitio}.
     *   Returns {@code Optional.empty()} if storage file is not found.
     * @throws DataConversionException if the data in storage is not in the expected format.
     * @throws IOException if there was any problem when reading from the storage.
     */
    Optional<ReadOnlyMalitio> readMalitio() throws DataConversionException, IOException;

    /**
     * @see #getMalitioFilePath()
     */
    Optional<ReadOnlyMalitio> readMalitio(String filePath) throws DataConversionException, IOException;

    /**
     * Saves the given {@link ReadOnlyMalitio} to the storage.
     * @param malitio cannot be null.
     * @throws IOException if there was any problem writing to the file.
     */
    void saveMalitio(ReadOnlyMalitio malitio) throws IOException;

    /**
     * @see #saveMalitio(ReadOnlyMalitio)
     */
    void saveMalitio(ReadOnlyMalitio malitio, String filePath) throws IOException;

}
