package seedu.flexitrack.storage;

import java.io.IOException;
import java.util.Optional;

import seedu.flexitrack.commons.exceptions.DataConversionException;
import seedu.flexitrack.model.ReadOnlyFlexiTrack;

/**
 * Represents a storage for {@link seedu.flexitrack.model.FlexiTrack}.
 */
public interface FlexiTrackStorage {

    /**
     * Returns the file path of the data file.
     */
    String getFlexiTrackFilePath();

    /**
     * Returns FlexiTrack data as a {@link ReadOnlyFlexiTrack}. Returns
     * {@code Optional.empty()} if storage file is not found.
     * 
     * @throws DataConversionException
     *             if the data in storage is not in the expected format.
     * @throws IOException
     *             if there was any problem when reading from the storage.
     */
    Optional<ReadOnlyFlexiTrack> readFlexiTrack() throws DataConversionException, IOException;

    /**
     * @see #getFlexiTrackFilePath()
     */
    Optional<ReadOnlyFlexiTrack> readFlexiTrack(String filePath) throws DataConversionException, IOException;

    /**
     * Saves the given {@link ReadOnlyFlexiTrack} to the storage.
     * 
     * @param FlexiTrack
     *            cannot be null.
     * @throws IOException
     *             if there was any problem writing to the file.
     */
    void saveFlexiTrack(ReadOnlyFlexiTrack flexiTrack) throws IOException;

    /**
     * @see #saveFlexiTrack(ReadOnlyFlexiTrack)
     */
    void saveFlexiTrack(ReadOnlyFlexiTrack flexiTrack, String filePath) throws IOException;

}
