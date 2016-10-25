package seedu.address.model;

import seedu.address.commons.core.UnmodifiableObservableList;
import seedu.address.commons.exceptions.StateLimitException;
import seedu.address.model.task.ReadOnlyTask;
import seedu.address.model.task.Task;
import seedu.address.model.task.UniqueTaskList;

import java.util.Set;

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
    
    /** Marks the given task as done*/
    void markTask(ReadOnlyTask task);

    /** Returns the filtered task list as an {@code UnmodifiableObservableList<ReadOnlyTask>} */
    UnmodifiableObservableList<ReadOnlyTask> getFilteredTaskList();

    /** Updates the filter of the filtered task list to show all tasks */
    void updateFilteredListToShowAll();

    /** Updates the filter of the filtered task list to filter by the given keywords*/
    void updateFilteredTaskList(Set<String> keywords);

	void updateFilteredTaskList(String type);
	
	/** Updates the filter of the filtered task list to filter by the given keywords of the given type*/
	void updateFilteredTaskList(String keyword, String type);
	
	/** Updates the filter of the filtered task list to filter by the given tags*/
    void updateFilteredTaskListByTags(Set<String> keywords);
	
	/** Update the task manager to the new file path*/
	void updateTaskManager(String filePath, boolean isToClearOld);

	/** Saves the current state of the task manager. */
    public void saveState(String message);
	
	/** Update the task manager to the previous state. */
	String getPreviousState() throws StateLimitException;
    
    /** Update the task manager to the next state. */
    String getNextState() throws StateLimitException;
    
    /**Update the task manager to show all up-to-date tasks. */
    void refreshTask();
	
}
