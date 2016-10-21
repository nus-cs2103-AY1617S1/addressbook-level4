package seedu.savvytasker.model;

import java.util.List;

import seedu.savvytasker.model.task.ReadOnlyTask;
import seedu.savvytasker.model.task.TaskList;

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
