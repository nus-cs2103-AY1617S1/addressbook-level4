package seedu.forgetmenot.model;


import java.util.List;

import seedu.forgetmenot.model.task.ReadOnlyTask;
import seedu.forgetmenot.model.task.UniqueTaskList;

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
