package seedu.unburden.model;


import java.util.List;

import seedu.unburden.model.person.ReadOnlyPerson;
import seedu.unburden.model.person.UniquePersonList;
import seedu.unburden.model.tag.Tag;
import seedu.unburden.model.tag.UniqueTagList;

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
