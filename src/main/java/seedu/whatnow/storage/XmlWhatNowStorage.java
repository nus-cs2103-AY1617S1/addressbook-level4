package seedu.whatnow.storage;
//@@author A0126240W
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Optional;
import java.util.logging.Logger;

import seedu.whatnow.commons.core.LogsCenter;
import seedu.whatnow.commons.exceptions.DataConversionException;
import seedu.whatnow.commons.util.FileUtil;
import seedu.whatnow.model.ReadOnlyWhatNow;

/**
 * A class to access WhatNow data stored as an xml file on the hard disk.
 */
public class XmlWhatNowStorage implements WhatNowStorage {

    private static final Logger logger = LogsCenter.getLogger(XmlWhatNowStorage.class);

    private String filePath;

    public XmlWhatNowStorage(String filePath){
        this.filePath = filePath;
    }

    public String getWhatNowFilePath(){
        return filePath;
    }
    
    /**
     * Sets the file path of the WhatNow data
     * @param filePath new location of the WhatNow data file.
     */
    public void setWhatNowFilePath(String filePath){
        this.filePath = filePath;
    }
    
    /**
     * Similar to {@link #readWhatNow()}
     * @param filePath location of the data. Cannot be null
     * @throws DataConversionException if the file is not in the correct format.
     */
    public Optional<ReadOnlyWhatNow> readWhatNow(String filePath) throws DataConversionException, FileNotFoundException {
        assert filePath != null;

        File whatNowFile = new File(filePath);

        if (!whatNowFile.exists()) {
            logger.info("WhatNow file "  + whatNowFile + " not found");
            return Optional.empty();
        }

        ReadOnlyWhatNow whatNowOptional = XmlFileStorage.loadDataFromSaveFile(new File(filePath));

        return Optional.of(whatNowOptional);
    }

    /**
     * Similar to {@link #saveWhatNow(ReadOnlyWhatNow)}
     * @param filePath location of the data. Cannot be null
     */
    public void saveWhatNow(ReadOnlyWhatNow whatNow, String filePath) throws IOException {
        assert whatNow != null;
        assert filePath != null;

        File file = new File(filePath);
        FileUtil.createIfMissing(file);
        XmlFileStorage.saveDataToFile(file, new XmlSerializableWhatNow(whatNow));
    }

    @Override
    public Optional<ReadOnlyWhatNow> readWhatNow() throws DataConversionException, IOException {
        return readWhatNow(filePath);
    }

    @Override
    public void saveWhatNow(ReadOnlyWhatNow whatNow) throws IOException {
        saveWhatNow(whatNow, filePath);
    }
}
