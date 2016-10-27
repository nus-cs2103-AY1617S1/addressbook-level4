package seedu.flexitrack.storage;

import java.io.IOException;
import java.util.Optional;

import seedu.flexitrack.commons.events.model.FlexiTrackChangedEvent;
import seedu.flexitrack.commons.events.storage.DataSavingExceptionEvent;
import seedu.flexitrack.commons.exceptions.DataConversionException;
import seedu.flexitrack.model.ReadOnlyFlexiTrack;
import seedu.flexitrack.model.UserPrefs;

/**
 * API of the Storage component
 */
public interface Storage extends FlexiTrackStorage, UserPrefsStorage {

    @Override
    Optional<UserPrefs> readUserPrefs() throws DataConversionException, IOException;

    @Override
    void saveUserPrefs(UserPrefs userPrefs) throws IOException;

    @Override
    String getFlexiTrackFilePath();

    @Override
    Optional<ReadOnlyFlexiTrack> readFlexiTrack() throws DataConversionException, IOException;

    @Override
    void saveFlexiTrack(ReadOnlyFlexiTrack flexiTrack) throws IOException;

    /**
     * Saves the current version of the FlexiTrack to the hard disk. Creates the
     * data file if it is missing. Raises {@link DataSavingExceptionEvent} if
     * there was an error during saving.
     */
    void handleFlexiTrackChangedEvent(FlexiTrackChangedEvent abce);
}
