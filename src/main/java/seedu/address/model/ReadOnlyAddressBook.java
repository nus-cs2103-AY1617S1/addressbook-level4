package seedu.address.model;


import seedu.address.model.item.ReadOnlyFloatingTask;
import seedu.address.model.person.UniqueFloatingTaskList;
import seedu.address.model.tag.Tag;
import seedu.address.model.tag.UniqueTagList;

import java.util.List;

/**
 * Unmodifiable view of an address book
 */
public interface ReadOnlyAddressBook {

    UniqueFloatingTaskList getUniqueFloatingTaskList();

    /**
     * Returns an unmodifiable view of persons list
     */
    List<ReadOnlyFloatingTask> getFloatingTaskList();

}
