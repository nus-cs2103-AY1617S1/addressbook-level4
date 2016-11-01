package seedu.whatnow.storage;

import java.io.IOException;
import java.util.Optional;

import seedu.whatnow.commons.exceptions.DataConversionException;
import seedu.whatnow.model.ReadOnlyWhatNow;

/**
 * Represents a storage for {@link seedu.whatnow.model.WhatNow}.
 */
public interface WhatNowStorage {

    /**
     * Returns the file path of the data file.
     */
    String getWhatNowFilePath();

    /**
     * Returns WhatNow data as a {@link ReadOnlyWhatNow}. Returns
     * {@code Optional.empty()} if storage file is not found.
     * 
     * @throws DataConversionException
     *             if the data in storage is not in the expected format.
     * @throws IOException
     *             if there was any problem when reading from the storage.
     */
    Optional<ReadOnlyWhatNow> readWhatNow() throws DataConversionException, IOException;

    /**
     * @see #getWhatNowFilePath()
     */
    Optional<ReadOnlyWhatNow> readWhatNow(String filePath) throws DataConversionException, IOException;

    /**
     * Saves the given {@link ReadOnlyWhatNow} to the storage.
     * 
     * @param whatNow
     *            cannot be null.
     * @throws IOException
     *             if there was any problem writing to the file.
     */
    void saveWhatNow(ReadOnlyWhatNow whatNow) throws IOException;

    /**
     * @see #saveWhatNow(ReadOnlyWhatNow)
     */
    void saveWhatNow(ReadOnlyWhatNow whatNow, String filePath) throws IOException;

}
