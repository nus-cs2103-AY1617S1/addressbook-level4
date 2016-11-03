package tars.storage;

import tars.commons.core.Config;
import tars.commons.events.model.TarsChangedEvent;
import tars.commons.events.storage.DataSavingExceptionEvent;
import tars.commons.exceptions.DataConversionException;
import tars.model.ReadOnlyTars;
import tars.model.UserPrefs;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Optional;

/**
 * API of the Storage component
 */
public interface Storage extends TarsStorage, UserPrefsStorage {

    @Override
    Optional<UserPrefs> readUserPrefs() throws DataConversionException, IOException;

    @Override
    void saveUserPrefs(UserPrefs userPrefs) throws IOException;

    @Override
    String getTarsFilePath();

    @Override
    Optional<ReadOnlyTars> readTars() throws DataConversionException, FileNotFoundException;

    @Override
    void saveTars(ReadOnlyTars tars) throws IOException;

    /**
     * Saves the current version of the Address Book to the hard disk.
     *   Creates the data file if it is missing.
     * Raises {@link DataSavingExceptionEvent} if there was an error during saving.
     */
    void handleTarsChangedEvent(TarsChangedEvent abce);
    
    /** Updates Tars Storage Directory
     * @@author A0124333U
     * @param newFilePath
     * @param newConfig
     */
    void updateTarsStorageDirectory(String newFilePath, Config newConfig);
    
    void saveTarsInNewFilePath(ReadOnlyTars tars, String newFilePath) throws IOException;
    
    boolean isFileSavedSuccessfully(String filePath);
    
    Optional<ReadOnlyTars> readTarsFromNewFilePath(String newFilePath)
        throws DataConversionException, FileNotFoundException;
}
