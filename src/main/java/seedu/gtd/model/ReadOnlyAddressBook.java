package seedu.gtd.model;


import java.util.List;

import seedu.gtd.model.person.ReadOnlyPerson;
import seedu.gtd.model.person.UniquePersonList;
import seedu.gtd.model.tag.Tag;
import seedu.gtd.model.tag.UniqueTagList;

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
