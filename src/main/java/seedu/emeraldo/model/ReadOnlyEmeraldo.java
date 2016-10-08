package seedu.emeraldo.model;


import seedu.address.model.tag.Tag;
import seedu.address.model.tag.UniqueTagList;
import seedu.emeraldo.model.person.ReadOnlyTask;
import seedu.emeraldo.model.person.UniquePersonList;

import java.util.List;

/**
 * Unmodifiable view of an address book
 */
public interface ReadOnlyEmeraldo {

    UniqueTagList getUniqueTagList();

    UniquePersonList getUniquePersonList();

    /**
     * Returns an unmodifiable view of persons list
     */
    List<ReadOnlyTask> getPersonList();

    /**
     * Returns an unmodifiable view of tags list
     */
    List<Tag> getTagList();

}
