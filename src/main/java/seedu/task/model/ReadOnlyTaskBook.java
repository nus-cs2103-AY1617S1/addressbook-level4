package seedu.task.model;


import java.util.List;

import seedu.task.model.item.ReadOnlyEvent;
import seedu.task.model.item.ReadOnlyTask;
import seedu.task.model.item.UniqueEventList;
import seedu.task.model.item.UniqueTaskList;

/**
 * Unmodifiable view of an task book
 */
public interface ReadOnlyTaskBook {

    UniqueTaskList getUniqueTaskList();
    UniqueEventList getUniqueEventList();
    /**
     * Returns an unmodifiable view of tasks list
     */
    List<ReadOnlyTask> getTaskList();

    /**
     * Returns an unmodifiable view of events list
     */
    List<ReadOnlyEvent> getEventList();
}
