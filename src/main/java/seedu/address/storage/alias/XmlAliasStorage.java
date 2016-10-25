package seedu.address.storage.alias;

import seedu.address.commons.collections.UniqueItemCollection;
import seedu.address.commons.core.LogsCenter;
import seedu.address.commons.exceptions.DataConversionException;
import seedu.address.commons.util.FileUtil;
import seedu.address.model.Alias;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Optional;
import java.util.logging.Logger;

/**
 * A class to access Alias data stored as an xml file on the hard disk.
 */
//@@author A0143107U
public class XmlAliasStorage implements AliasStorage {

    private static final Logger logger = LogsCenter.getLogger(XmlAliasStorage.class);

    private String filePath;

    public XmlAliasStorage(String filePath){
        this.filePath = filePath;
    }

    public String getAliasFilePath(){
        return filePath;
    }

    /**
     * Similar to {@link #UniqueItemCollection<Alias>}
     * @param filePath location of the data. Cannot be null
     * @throws DataConversionException if the file is not in the correct format.
     */
    public Optional<UniqueItemCollection<Alias>> readAlias(String filePath) throws DataConversionException, FileNotFoundException {
        assert filePath != null;

        File aliasFile = new File(filePath);

        if (!aliasFile.exists()) {
            logger.info("Alias file "  + aliasFile + " not found");
            return Optional.empty();
        }
        
        UniqueItemCollection<Alias> aliasOptional = XmlAliasFileStorage.loadDataFromSaveFile(new File(filePath));

        return Optional.of(aliasOptional);
    }

    /**
     * Similar to {@link #saveAlias(UniqueItemCollection<Alias>)}
     * @param filePath location of the data. Cannot be null
     */
    public void saveAlias(UniqueItemCollection<Alias> alias, String filePath) throws IOException {
        assert alias != null;
        assert filePath != null;

        File file = new File(filePath);
        FileUtil.createIfMissing(file);
        XmlAliasFileStorage.saveDataToFile(file, new XmlSerializableAlias(alias));
    }

    @Override
    public Optional<UniqueItemCollection<Alias>> readAlias() throws DataConversionException, IOException {
        return readAlias(filePath);
    }

    @Override
    public void saveAlias(UniqueItemCollection<Alias> alias) throws IOException {
        saveAlias(alias, filePath);
    }
}
