package harmony.model;


import java.util.List;

import harmony.model.person.ReadOnlyPerson;
import harmony.model.person.UniquePersonList;
import harmony.model.tag.Tag;
import harmony.model.tag.UniqueTagList;

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
