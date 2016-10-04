package seedu.task.model;


import java.util.List;

import seedu.task.model.person.ReadOnlyPerson;
import seedu.task.model.person.UniquePersonList;
import seedu.task.model.tag.Tag;
import seedu.task.model.tag.UniqueTagList;

/**
 * Unmodifiable view of an address book
 */
public interface ReadOnlyAddressBook {

    UniqueTagList getUniqueTagList();

    UniquePersonList getUniquePersonList();

    /**
     * Returns an unmodifiable view of persons list
     */
    List<ReadOnlyPerson> getPersonList();

    /**
     * Returns an unmodifiable view of tags list
     */
    List<Tag> getTagList();

}
