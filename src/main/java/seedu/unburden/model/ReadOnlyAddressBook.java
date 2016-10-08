package seedu.unburden.model;


import java.util.List;

import seedu.unburden.model.person.ReadOnlyTask;
import seedu.unburden.model.person.UniqueTaskList;
import seedu.unburden.model.tag.Tag;
import seedu.unburden.model.tag.UniqueTagList;

/**
 * Unmodifiable view of an address book
 */
public interface ReadOnlyAddressBook {

    UniqueTagList getUniqueTagList();

    UniqueTaskList getUniqueTaskList();

    /**
     * Returns an unmodifiable view of persons list
     */
    List<ReadOnlyTask> getPersonList();

    /**
     * Returns an unmodifiable view of tags list
     */
    List<Tag> getTagList();

}
