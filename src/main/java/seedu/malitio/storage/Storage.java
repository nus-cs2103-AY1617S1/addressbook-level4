package seedu.malitio.storage;

import seedu.malitio.commons.events.model.MalitioChangedEvent;
import seedu.malitio.commons.events.storage.DataSavingExceptionEvent;
import seedu.malitio.commons.exceptions.DataConversionException;
import seedu.malitio.model.ReadOnlyMalitio;
import seedu.malitio.model.UserPrefs;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Optional;

/**
 * API of the Storage component
 */
public interface Storage extends MalitioStorage, UserPrefsStorage {

    @Override
    Optional<UserPrefs> readUserPrefs() throws DataConversionException, IOException;

    @Override
    void saveUserPrefs(UserPrefs userPrefs) throws IOException;

    @Override
    String getMalitioFilePath();

    @Override
    Optional<ReadOnlyMalitio> readMalitio() throws DataConversionException, IOException;

    @Override
    void saveMalitio(ReadOnlyMalitio malitio) throws IOException;

    /**
     * Saves the current version of the malitio to the hard disk.
     *   Creates the data file if it is missing.
     * Raises {@link DataSavingExceptionEvent} if there was an error during saving.
     */
    void handleMalitioChangedEvent(MalitioChangedEvent abce);
}
