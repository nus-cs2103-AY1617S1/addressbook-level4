package seedu.flexitrack.storage;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Optional;
import java.util.logging.Logger;

import seedu.flexitrack.commons.core.LogsCenter;
import seedu.flexitrack.commons.exceptions.DataConversionException;
import seedu.flexitrack.commons.util.FileUtil;
import seedu.flexitrack.model.ReadOnlyFlexiTrack;

/**
 * A class to access FlexiTrack data stored as an xml file on the hard disk.
 */
public class XmlFlexiTrackStorage implements FlexiTrackStorage {

    private static final Logger logger = LogsCenter.getLogger(XmlFlexiTrackStorage.class);

    private String filePath;

    public XmlFlexiTrackStorage(String filePath) {
        this.filePath = filePath;
    }

    public String getFlexiTrackFilePath() {
        return filePath;
    }

    /**
     * Similar to {@link #readFlexiTrack()}
     * 
     * @param filePath
     *            location of the data. Cannot be null
     * @throws DataConversionException
     *             if the file is not in the correct format.
     */
    public Optional<ReadOnlyFlexiTrack> readFlexiTrack(String filePath)
            throws DataConversionException, FileNotFoundException {
        assert filePath != null;

        File flexiTrackFile = new File(filePath);

        if (!flexiTrackFile.exists()) {
            logger.info("FlexiTrack file " + flexiTrackFile + " not found");
            return Optional.empty();
        }

        ReadOnlyFlexiTrack flexiTrackOptional = XmlFileStorage.loadDataFromSaveFile(new File(filePath));

        return Optional.of(flexiTrackOptional);
    }

    /**
     * Similar to {@link #saveFlexiTrack(ReadOnlyFlexiTrack)}
     * 
     * @param filePath
     *            location of the data. Cannot be null
     */
    public void saveFlexiTrack(ReadOnlyFlexiTrack flexiTrack, String filePath) throws IOException {
        assert flexiTrack != null;
        assert filePath != null;

        File file = new File(filePath);
        FileUtil.createIfMissing(file);
        XmlFileStorage.saveDataToFile(file, new XmlSerializableFlexiTrack(flexiTrack));
    }

    @Override
    public Optional<ReadOnlyFlexiTrack> readFlexiTrack() throws DataConversionException, IOException {
        return readFlexiTrack(filePath);
    }

    @Override
    public void saveFlexiTrack(ReadOnlyFlexiTrack flexiTrack) throws IOException {
        saveFlexiTrack(flexiTrack, filePath);
    }
}
