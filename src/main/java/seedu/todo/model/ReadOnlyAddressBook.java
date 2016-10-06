package seedu.todo.model;


import java.util.List;

import seedu.todo.model.person.ReadOnlyPerson;
import seedu.todo.model.person.UniquePersonList;
import seedu.todo.model.tag.Tag;
import seedu.todo.model.tag.UniqueTagList;

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
