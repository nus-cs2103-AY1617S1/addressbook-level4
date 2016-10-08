package seedu.task.model;


import java.util.List;

import seedu.task.model.event.ReadOnlyEvent;
import seedu.task.model.event.UniqueEventList;
import seedu.task.model.task.ReadOnlyTask;
import seedu.task.model.task.UniqueTaskList;

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
