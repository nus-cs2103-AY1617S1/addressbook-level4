package seedu.address.model;


import seedu.address.model.item.ReadOnlyTask;
import seedu.address.model.item.UniqueTaskList;

import java.util.List;

/**
 * Unmodifiable view of an address book
 */
public interface ReadOnlyTaskManager {

    UniqueTaskList getUniqueUndoneTaskList();
    UniqueTaskList getUniqueDoneTaskList();

    /**
     * Returns an unmodifiable view of persons list
     */
    List<ReadOnlyTask> getUndoneTaskList();
    List<ReadOnlyTask> getDoneTaskList();

}
