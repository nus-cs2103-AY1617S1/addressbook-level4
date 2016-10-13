package seedu.savvytasker.model;


import java.util.List;

import seedu.savvytasker.model.person.ReadOnlyPerson;
import seedu.savvytasker.model.person.UniquePersonList;
import seedu.savvytasker.model.tag.Tag;
import seedu.savvytasker.model.tag.UniqueTagList;

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
