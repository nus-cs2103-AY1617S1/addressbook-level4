package seedu.address.model;

import seedu.address.commons.core.UnmodifiableObservableList;
import seedu.address.commons.events.ui.FilterPanelChangedEvent;
import seedu.address.commons.exceptions.IllegalValueException;
import seedu.address.commons.exceptions.StateLimitException;
import seedu.address.commons.util.Types;
import seedu.address.model.task.ReadOnlyTask;
import seedu.address.model.task.Task;
import seedu.address.model.task.UniqueTaskList;

import java.util.Map;
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
    void addTask(Task task);

    /** Marks the given task as done*/
    void markTask(ReadOnlyTask task);

    /** Returns the filtered task list as an {@code UnmodifiableObservableList<ReadOnlyTask>} */
    UnmodifiableObservableList<ReadOnlyTask> getFilteredTaskList();

    /** Updates the filter of the filtered task list to show all tasks */
    void updateFilteredListToShowAll();

    /** Updates the filter of the filtered task list to filter by the given keywords */
    void updateFilteredTaskList(Set<String> keywords);

    // @@author A0146123R
    /**
     * Updates the filter of the filtered task list to filter by the given type
     */
    void updateFilteredTaskList(Types type);

    /**
     * Updates the filter of the filtered task list to filter by multiple
     * qualifications
     */
    void updateFilteredTaskList(Map<Types, String> qualifications, Set<String> tags);
    
    /**
     * Updates the filter of the filtered task list to filter by multiple
     * types and qualifications
     */
    void updateFilteredTaskList(Set<Types> types, Map<Types, String> qualifications, Set<String> tags);

    /**
     * Updates the filter of the filtered task list to filter by the the given
     * keywords (for find command)
     */
    void updateFilteredTaskListWithKeywords(Set<Set<String>> keywordsGroups);

    /**
     * Updates the filter of the filtered task list to filter by the stemmed
     * words of the given keywords (for find command)
     */
    void updateFilteredTaskListWithStemmedKeywords(Set<Set<String>> keywordsGroups);

    /** Update the task manager to the new file path */
    void updateTaskManager(String filePath, boolean isToClearOld);

    /**
     * Change the task manager back to the old file path
     * 
     * @throws StateLimitException
     */
    void changeBackTaskManager(boolean isToClearNew) throws StateLimitException;

    /**
     * Redo update the task manager to the new file path
     * 
     * @throws StateLimitException
     */
    void redoUpdateTaskManager() throws StateLimitException;

    /** Saves the current state of the task manager. */
    public void saveState(String message);

    /**
     * Update the task manager to the previous state.
     * 
     * @return String message
     * @throws StateLimitException
     */
    String getPreviousState() throws StateLimitException;

    /**
     * Update the task manager to the next state.
     * 
     * @return String message
     * @throws StateLimitException
     */
    String getNextState() throws StateLimitException;

    /**
     * Redo saves the current version of the Task Manager to the new file in
     * hard disk. Delete the new data file if it was previously specified.
     */
    void handleFilterPanelChangedEvent(FilterPanelChangedEvent abce);

    //@@author A0142325R
    /** Update the task manager to show all up-to-date tasks. */
    void refreshTask();
}
