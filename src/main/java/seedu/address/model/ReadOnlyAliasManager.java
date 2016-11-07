package seedu.address.model;

//@@author A0143756Y-reused
import java.util.List;

import seedu.address.model.alias.ReadOnlyAlias;
import seedu.address.model.alias.UniqueAliasList;

/**
 * Unmodifiable view of an alias manager.
 */
public interface ReadOnlyAliasManager {

    UniqueAliasList getUniqueAliasList();

    /**
     * Returns an unmodifiable view of alias list
     */
    List<ReadOnlyAlias> getAliasList();
}
