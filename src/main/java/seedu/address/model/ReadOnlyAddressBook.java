package seedu.address.model;


import seedu.address.model.person.ReadOnlyTask;
import seedu.address.model.person.UniquePersonList;
import seedu.address.model.tag.Tag;
import seedu.address.model.tag.UniqueTagList;

import java.util.List;

/**
 * Unmodifiable view of an address book
 */
public interface ReadOnlyAddressBook {

    UniqueTagList getUniqueTagList();

    UniquePersonList getUniquePersonList();
    
    UniquePersonList getUniqueUndatedTaskList();

    /**
     * Returns an unmodifiable view of persons list
     */
    List<ReadOnlyTask> getPersonList();
    
    /**
     * Returns an unmodifiable view of persons list
     */
    List<ReadOnlyTask> getUndatedTaskList();

    /**
     * Returns an unmodifiable view of tags list
     */
    List<Tag> getTagList();

}
