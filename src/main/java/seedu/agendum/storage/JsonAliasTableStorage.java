package seedu.agendum.storage;

import seedu.agendum.commons.core.LogsCenter;
import seedu.agendum.commons.exceptions.DataConversionException;
import seedu.agendum.commons.util.FileUtil;
import seedu.agendum.model.UserPrefs;

import java.io.File;
import java.io.IOException;
import java.util.Hashtable;
import java.util.Optional;
import java.util.logging.Logger;

/**
 * A class to access the alias (command) table stored in the hard disk as a json file
 */
public class JsonAliasTableStorage implements AliasTableStorage {

    private static final Logger logger = LogsCenter.getLogger(JsonAliasTableStorage.class);

    private String filePath;

    public JsonAliasTableStorage(String filePath) {
        this.filePath = filePath;
    }

    @Override
    public Optional<Hashtable<String, String>> readAliasTable() throws DataConversionException, IOException {
        return readAliasTable(filePath);
    }

    @Override
    public void saveAliasTable(Hashtable<String, String> table) throws IOException {
        saveAliasTable(table, filePath);
    }

    /**
     * Similar to {@link #readAliasTable()}
     * @param aliasTableFilePath location of the command library's alias table data. Cannot be null.
     * @throws DataConversionException if the file format is not as expected.
     */
    public Optional<Hashtable<String, String>> readAliasTable(String aliasTableFilePath)
            throws DataConversionException {
        assert aliasTableFilePath != null;

        File aliasTableFile = new File(aliasTableFilePath);

        if (!aliasTableFile.exists()) {
            logger.info("Alias Table file: "  + aliasTableFile + " not found");
            return Optional.empty();
        }

        Hashtable<String, String> table = new Hashtable<String, String>();

        try {
            table = FileUtil.deserializeObjectFromJsonFile(aliasTableFile, table.getClass());
        } catch (IOException e) {
            logger.warning("Error reading from alias table file " + aliasTableFile + ": " + e);
            throw new DataConversionException(e); 
        }

        return Optional.of(table);
    }

    /**
     * Similar to {@link #saveAliasTable(Hashtable<String, String> table)}
     * @param aliasTableFilePath location of the command library's alias table data. Cannot be null.
     */
    public void saveAliasTable(Hashtable<String, String> table, String aliasTableFilePath)
            throws IOException {
        assert table != null;
        assert aliasTableFilePath != null;

        FileUtil.serializeObjectToJsonFile(new File(aliasTableFilePath), table);
    }
}
