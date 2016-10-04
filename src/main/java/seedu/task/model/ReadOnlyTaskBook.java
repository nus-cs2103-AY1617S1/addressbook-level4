package seedu.task.model;


import java.util.List;

import seedu.task.model.task.ReadOnlyTask;
import seedu.task.model.task.UniqueTaskList;

/**
 * Unmodifiable view of an task book
 */
public interface ReadOnlyTaskBook {

    UniqueTaskList getUniqueTaskList();

    /**
     * Returns an unmodifiable view of tasks list
     */
    List<ReadOnlyTask> getTaskList();

}
