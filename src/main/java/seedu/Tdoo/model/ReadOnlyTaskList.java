package seedu.Tdoo.model;

import java.util.List;

import seedu.Tdoo.model.task.ReadOnlyTask;
import seedu.Tdoo.model.task.UniqueTaskList;

/**
 * Unmodifiable view of an TodoList
 */
public interface ReadOnlyTaskList {

	UniqueTaskList getUniqueTaskList();

	/**
	 * Returns an unmodifiable view of tasks list
	 */
	List<ReadOnlyTask> getTaskList();
}
