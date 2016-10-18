package seedu.agendum.model;


import seedu.agendum.model.task.ReadOnlyTask;
import seedu.agendum.model.task.UniqueTaskList;

import java.util.List;

/**
 * Unmodifiable view of a to do list
 */
public interface ReadOnlyToDoList {

    UniqueTaskList getUniqueTaskList();

    /**
     * Returns an unmodifiable view of tasks list
     */
    List<ReadOnlyTask> getTaskList();

}
