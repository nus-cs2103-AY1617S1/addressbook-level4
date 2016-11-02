package seedu.address.storage;

import seedu.address.commons.core.LogsCenter;
import seedu.address.commons.exceptions.DataConversionException;
import seedu.address.commons.util.FileUtil;
import seedu.address.model.AliasManager;
import seedu.address.model.ReadOnlyAliasManager;
import seedu.address.model.ReadOnlyTaskManager;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Optional;
import java.util.logging.Logger;

//@@author A0143756Y-reused
/**
 * A class to access AliasManager data stored as an xml file on the hard disk.
 */
public class XmlAliasManagerStorage implements AliasManagerStorage {

    private static final Logger logger = LogsCenter.getLogger(XmlAliasManagerStorage.class);

    private String filePath;

    public XmlAliasManagerStorage(String filePath){
        this.filePath = filePath;
    }

    public String getAliasManagerFilePath(){
        return filePath;
    }

    /**
     * Similar to {@link #readAliasManager()}
     * @param filePath location of the data. Cannot be null
     * @throws DataConversionException if the file is not in the correct format.
     */
    public Optional<ReadOnlyAliasManager> readAliasManager(String filePath) throws DataConversionException, FileNotFoundException {
        assert filePath != null;

        File aliasFile = new File(filePath);

        if (!aliasFile.exists()) {
            logger.info("AliasManager file "  + aliasFile + " not found");
            return Optional.empty();
        }

        ReadOnlyAliasManager aliasOptional = XmlFileStorage.loadDataFromSaveAliasFile(new File(filePath));

        return Optional.of(aliasOptional);
    }

    /**
     * Similar to {@link #saveAliasManager(ReadOnlyTaskManager)}
     * @param filePath location of the data. Cannot be null
     */
    public void saveAliasManager(ReadOnlyAliasManager alias, String filePath) throws IOException {
        assert alias != null;
        assert filePath != null;

        File file = new File(filePath);
        FileUtil.createIfMissing(file);
        XmlFileStorage.saveDataToFile(file, new XmlSerializableAliasManager(alias));
    }

    @Override
    public Optional<ReadOnlyAliasManager> readAliasManager() throws DataConversionException, IOException {
        return readAliasManager(filePath);
    }

    @Override
    public void saveAliasManager(ReadOnlyAliasManager alias) throws IOException {
        saveAliasManager(alias, filePath);
    }

}
