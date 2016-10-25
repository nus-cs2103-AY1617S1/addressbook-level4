package seedu.todo.model;


import seedu.todo.model.person.ReadOnlyPerson;
import seedu.todo.model.person.UniquePersonList;
import seedu.todo.model.tag.Tag;
import seedu.todo.model.tag.UniqueTagCollection;

import java.util.List;

/**
 * Unmodifiable view of an address book
 */
public interface ReadOnlyAddressBook {

    UniqueTagCollection getUniqueTagList();

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
