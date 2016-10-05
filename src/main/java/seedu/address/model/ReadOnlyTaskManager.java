package seedu.address.model;


import seedu.address.model.item.ReadOnlyFloatingTask;
import seedu.address.model.person.UniqueFloatingTaskList;

import java.util.List;

/**
 * Unmodifiable view of an address book
 */
public interface ReadOnlyTaskManager {

    UniqueFloatingTaskList getUniqueFloatingTaskList();

    /**
     * Returns an unmodifiable view of persons list
     */
    List<ReadOnlyFloatingTask> getFloatingTaskList();

}
