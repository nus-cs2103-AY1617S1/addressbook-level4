package seedu.address.model;

import seedu.address.commons.core.UnmodifiableObservableList;
import seedu.address.commons.exceptions.IllegalValueException;
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

    /** Edit the given task
     * @throws IllegalValueException */
	void editTask(ReadOnlyTask task, String type, String details) throws IllegalValueException;

    /** Adds the given task */
    void addTask(Task task) throws UniqueTaskList.DuplicateTaskException;

    /** Marks the given task as done*/
    void markTask(ReadOnlyTask task);

    /** Returns the filtered task list as an {@code UnmodifiableObservableList<ReadOnlyTask>} */
    UnmodifiableObservableList<ReadOnlyTask> getFilteredTaskList();

    /** Updates the filter of the filtered task list to show all tasks */
    void updateFilteredListToShowAll();

    /** Updates the filter of the filtered task list to filter by the given keywords */
    void updateFilteredTaskList(Set<String> keywords);

    //@@author A0146123R
    /** Updates the filter of the filtered task list to filter by the given type */
	void updateFilteredTaskList(String type);
	
	/** Updates the filter of the filtered task list to filter by the given keywords of the given type */
	void updateFilteredTaskList(String keyword, String type);
	
	/** Updates the filter of the filtered task list to filter by the the given keywords (for find command) */
    void updateFilteredTaskListWithKeywords(Set<Set<String>> keywordsGroups);
	
	/** Updates the filter of the filtered task list to filter by the stemmed words of the given keywords (for find command) */
    void updateFilteredTaskListWithStemmedKeywords(Set<Set<String>> keywordsGroups);
	
    /** Updates the filter of the filtered task list to filter by the given tags */
    void updateFilteredTaskListByTags(Set<String> keywords);

	/** Update the task manager to the new file path */
	void updateTaskManager(String filePath, boolean isToClearOld);
	
	/** Change the task manager back to the old file path*/
    void changeBackTaskManager(boolean isToClearNew);
    
    /** Redo update the task manager back to the new file path*/
    void redoUpdateTaskManager(boolean isToClearOld);

	/** Saves the current state of the task manager. */
    public void saveState(String message);

	/** Update the task manager to the previous state. */
	String getPreviousState() throws StateLimitException;

    /** Update the task manager to the next state. */
    String getNextState() throws StateLimitException;

    //@@author A0142325R
    /** Update the task manager to show all up-to-date tasks. */
    void refreshTask();
}
