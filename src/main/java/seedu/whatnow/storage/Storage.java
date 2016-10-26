package seedu.whatnow.storage;
//@@author A0141021H-reused
import java.io.IOException;
import java.nio.file.Path;
import java.util.Optional;

import seedu.whatnow.commons.core.Config;
import seedu.whatnow.commons.events.model.ConfigChangedEvent;
import seedu.whatnow.commons.events.model.WhatNowChangedEvent;
import seedu.whatnow.commons.events.storage.DataSavingExceptionEvent;
import seedu.whatnow.commons.exceptions.DataConversionException;
import seedu.whatnow.model.ReadOnlyWhatNow;
import seedu.whatnow.model.UserPrefs;

/**
 * API of the Storage component
 */
public interface Storage extends WhatNowStorage, UserPrefsStorage {

    @Override
    Optional<UserPrefs> readUserPrefs() throws DataConversionException, IOException;

    @Override
    void saveUserPrefs(UserPrefs userPrefs) throws IOException;

    @Override
    String getWhatNowFilePath();

    @Override
    Optional<ReadOnlyWhatNow> readWhatNow() throws DataConversionException, IOException;

    @Override
    void saveWhatNow(ReadOnlyWhatNow whatNow) throws IOException;

    /**
     * Saves the current version of the WhatNow to the hard disk.
     *   Creates the data file if it is missing.
     * Raises {@link DataSavingExceptionEvent} if there was an error during saving.
     */
    void handleWhatNowChangedEvent(WhatNowChangedEvent abce);
    
    void handleFileLocationChangedEvent(ConfigChangedEvent dfgh);

    void saveConfig(Config config) throws IOException;

    void saveConfig(Config config, String filePath) throws IOException;
}
