package seedu.emeraldo.storage;

import seedu.emeraldo.commons.events.model.EmeraldoChangedEvent;
import seedu.emeraldo.commons.events.storage.DataSavingExceptionEvent;
import seedu.emeraldo.commons.exceptions.DataConversionException;
import seedu.emeraldo.model.ReadOnlyEmeraldo;
import seedu.emeraldo.model.UserPrefs;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Optional;

/**
 * API of the Storage component
 */
public interface Storage extends EmeraldoStorage, UserPrefsStorage {

    @Override
    Optional<UserPrefs> readUserPrefs() throws DataConversionException, IOException;

    @Override
    void saveUserPrefs(UserPrefs userPrefs) throws IOException;

    @Override
    String getEmeraldoFilePath();
    
    void changeEmeraldoFilePath(String filepath);

    @Override
    Optional<ReadOnlyEmeraldo> readEmeraldo() throws DataConversionException, IOException;

    @Override
    void saveEmeraldo(ReadOnlyEmeraldo emeraldo) throws IOException;

    /**
     * Saves the current version of the Emeraldo to the hard disk.
     *   Creates the data file if it is missing.
     * Raises {@link DataSavingExceptionEvent} if there was an error during saving.
     */
    void handleEmeraldoChangedEvent(EmeraldoChangedEvent abce);
}
