package seedu.address.model;

import seedu.address.model.person.ReadOnlyTask;
import seedu.address.model.person.TaskList;

import java.util.List;

/**
 * Unmodifiable view of a task list
 */
public interface ReadOnlySavvyTasker {

    TaskList getTaskList();
    
    /**
     * Returns an unmodifiable view of task list
     */
    List<ReadOnlyTask> getReadOnlyListOfTasks();

}
