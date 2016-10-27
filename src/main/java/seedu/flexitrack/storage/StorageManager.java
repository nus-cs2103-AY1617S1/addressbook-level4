package seedu.flexitrack.storage;

import java.io.IOException;
import java.util.Optional;
import java.util.logging.Logger;

import com.google.common.eventbus.Subscribe;

import seedu.flexitrack.commons.core.ComponentManager;
import seedu.flexitrack.commons.core.LogsCenter;
import seedu.flexitrack.commons.events.model.FlexiTrackChangedEvent;
import seedu.flexitrack.commons.events.storage.DataSavingExceptionEvent;
import seedu.flexitrack.commons.exceptions.DataConversionException;
import seedu.flexitrack.model.ReadOnlyFlexiTrack;
import seedu.flexitrack.model.UserPrefs;

/**
 * Manages storage of FlexiTrack data in local storage.
 */
public class StorageManager extends ComponentManager implements Storage {

    private static final Logger logger = LogsCenter.getLogger(StorageManager.class);
    private FlexiTrackStorage flexiTrackStorage;
    private UserPrefsStorage userPrefsStorage;

    public StorageManager(FlexiTrackStorage flexiTrackStorage, UserPrefsStorage userPrefsStorage) {
        super();
        this.flexiTrackStorage = flexiTrackStorage;
        this.userPrefsStorage = userPrefsStorage;
    }

    public StorageManager(String flexiTrackFilePath, String userPrefsFilePath) {
        this(new XmlFlexiTrackStorage(flexiTrackFilePath), new JsonUserPrefsStorage(userPrefsFilePath));
    }

    // ================ UserPrefs methods ==============================

    @Override
    public Optional<UserPrefs> readUserPrefs() throws DataConversionException, IOException {
        return userPrefsStorage.readUserPrefs();
    }

    @Override
    public void saveUserPrefs(UserPrefs userPrefs) throws IOException {
        userPrefsStorage.saveUserPrefs(userPrefs);
    }

    // ================ FlexiTrack methods ==============================

    @Override
    public String getFlexiTrackFilePath() {
        return flexiTrackStorage.getFlexiTrackFilePath();
    }

    @Override
    public Optional<ReadOnlyFlexiTrack> readFlexiTrack() throws DataConversionException, IOException {
        return readFlexiTrack(flexiTrackStorage.getFlexiTrackFilePath());
    }

    @Override
    public Optional<ReadOnlyFlexiTrack> readFlexiTrack(String filePath) throws DataConversionException, IOException {
        logger.fine("Attempting to read data from file: " + filePath);
        return flexiTrackStorage.readFlexiTrack(filePath);
    }

    @Override
    public void saveFlexiTrack(ReadOnlyFlexiTrack flexiTrack) throws IOException {
        saveFlexiTrack(flexiTrack, flexiTrackStorage.getFlexiTrackFilePath());
    }

    @Override
    public void saveFlexiTrack(ReadOnlyFlexiTrack flexiTrack, String filePath) throws IOException {
        logger.fine("Attempting to write to data file: " + filePath);
        flexiTrackStorage.saveFlexiTrack(flexiTrack, filePath);
    }

    @Override
    @Subscribe
    public void handleFlexiTrackChangedEvent(FlexiTrackChangedEvent event) {
        logger.info(LogsCenter.getEventHandlingLogMessage(event, "Local data changed, saving to file"));
        try {
            saveFlexiTrack(event.data);
        } catch (IOException e) {
            raise(new DataSavingExceptionEvent(e));
        }
    }

}
