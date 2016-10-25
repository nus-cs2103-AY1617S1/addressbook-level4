package seedu.address.model;


import seedu.address.model.task.ReadOnlyTask;
import seedu.address.model.task.UniqueTaskList;

import java.util.List;

/**
 * Unmodifiable view of a task manager
 */
public interface ReadOnlyTaskManager {

    UniqueTaskList getUniqueTaskList();

    /**
     * Returns an unmodifiable view of tasks list
     */
    List<ReadOnlyTask> getTaskList();

}
