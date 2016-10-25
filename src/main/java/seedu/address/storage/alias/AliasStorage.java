package seedu.address.storage.alias;

import seedu.address.commons.collections.UniqueItemCollection;
import seedu.address.commons.exceptions.DataConversionException;
import seedu.address.model.Alias;

import java.io.IOException;
import java.util.Optional;

/**
 * Represents a storage for {@link seedu.address.model.Alias}.
 */
//@@author A0143107U
public interface AliasStorage {

    /**
     * Returns the file path of the data file.
     */
    String getAliasFilePath();

    /**
     * Returns Alias data from storage.
     *   Returns {@code Optional.empty()} if storage file is not found.
     * @throws DataConversionException if the data in storage is not in the expected format.
     * @throws IOException if there was any problem when reading from the storage.
     */
    Optional<UniqueItemCollection<Alias>> readAlias() throws DataConversionException, IOException;
    
    /**
     * @see #getAliasFilePath()
     */
    Optional<UniqueItemCollection<Alias>> readAlias(String filePath) throws DataConversionException, IOException;


    /**
     * Saves the given {{@link UniqueItemCollection<Alias>} to the storage.
     * @param alias cannot be null.
     * @throws IOException if there was any problem writing to the file.
     */
    void saveAlias(UniqueItemCollection<Alias> alias) throws IOException;
    
    /**
     * @see #saveAlias(UniqueItemCollection<Alias>)
     */
    void saveAlias(UniqueItemCollection<Alias> alias, String filepath) throws IOException;

    
}
