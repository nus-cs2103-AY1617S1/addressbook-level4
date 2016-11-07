package seedu.address.model;

import seedu.address.commons.core.UnmodifiableObservableList;
import seedu.address.model.task.ReadOnlyTask;
import seedu.address.model.task.Task;
import seedu.address.model.task.UniqueTaskList;
import seedu.address.model.task.UniqueTaskList.DuplicateTaskException;

import java.util.Set;
import seedu.address.commons.exceptions.FinishStateException;

/**
 * The API of the Model component.
 */
public interface Model {
    /** Clears existing backing model and replaces with the provided new data. */
    void resetData(ReadOnlyTaskManager newData);

    /** Returns the TaskManager */
    ReadOnlyTaskManager getTaskManager();

    /** Deletes the given task. */
    void deleteTask(ReadOnlyTask target) throws UniqueTaskList.TaskNotFoundException;

    /** Adds the given task */
    void addTask(Task task) throws UniqueTaskList.DuplicateTaskException;

    /** Returns the filtered task list as an {@code UnmodifiableObservableList<ReadOnlyTask>} */
    UnmodifiableObservableList<ReadOnlyTask> getFilteredTaskList();

    /** Updates the filter of the filtered task list to show all persons */
    void updateFilteredListToShowAll();

    /** Updates the filter of the filtered task list to filter by the given keywords*/
    void updateFilteredTaskList(Set<String> keywords);

    /** Updates the filter of the filtered task list to show tasks with clashing deadlines
     * @throws DuplicateTaskException */
    void updateFilteredListToShowClashing() throws DuplicateTaskException;

	void updateFilteredListToShowIncompleteTask() throws DuplicateTaskException;
	
	/** Updates the filter of the filtered task list to show tasks relevant to the specific keyword.*/
	void updateFilteredListToShowUncompleteAndKeywordTasks(String keyword) throws DuplicateTaskException;

	void updateFilteredTaskGroup(String keywords);
	
	/** Saves the task manager's current state*/
	public void currentState(String message);
	
	/** Updates the task manager a previous state.*/
	String getPreviousState() throws FinishStateException;
	
	/** Update the task manager back to the initial state.*/
	String getInitialState() throws FinishStateException;
}
