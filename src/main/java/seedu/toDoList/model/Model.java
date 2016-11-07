package seedu.toDoList.model;

import seedu.toDoList.commons.core.UnmodifiableObservableList;
import seedu.toDoList.commons.events.ui.FilterPanelChangedEvent;
import seedu.toDoList.commons.exceptions.IllegalValueException;
import seedu.toDoList.commons.exceptions.StateLimitException;
import seedu.toDoList.commons.util.Types;
import seedu.toDoList.model.task.ReadOnlyTask;
import seedu.toDoList.model.task.Task;
import seedu.toDoList.model.task.TaskList;

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
    void deleteTask(ReadOnlyTask target) throws TaskList.TaskNotFoundException;

    //@@author A0138717X
    /** Edits the given task
     * @throws IllegalValueException */
	void editTask(ReadOnlyTask task, String type, String details) throws IllegalValueException;
	//@@author

    /** Adds the given task. */ 
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
    /** Updates the filter of the filtered task list to filter by the given type. */
    void updateFilteredTaskListByType(Types type);

    /** Updates the filter of the filtered task list to filter by multiple qualifications. */
    void updateFilteredTaskListByQualifications(Map<Types, String> qualifications, Set<String> tags);
    
    /** Updates the filter of the filtered task list to filter by multiple types and qualifications. */
    void updateFilteredTaskListByTypesAndQualifications(Set<Types> types, Map<Types, String> qualifications,
            Set<String> tags);

    /** 
     * Updates the filter of the filtered task list to filter by the the given 
     * keywords (for find command).
     */
    void updateFilteredTaskListWithKeywords(Set<Set<String>> keywordsGroups);

    /**
     * Updates the filter of the filtered task list to filter by the stemmed
     * words of the given keywords (for find command). (Stemming is the process
     * of reducing inflected (or sometimes derived) words to their word stem.
     * Example: "stems", "stemmer", "stemming", "stemmed" as based on "stem")
     */
    void updateFilteredTaskListWithStemmedKeywords(Set<Set<String>> keywordsGroups);

    /**
     * Updates the task manager to the new file path and deletes the data file
     * in the previous file path if it is specified.
     */
    void updateTaskManagerToPath(String filePath, boolean isToClearOld);

    /**
     * Changes the task manager back to the old file path and deletes the data
     * file in the new file path if it is specified.
     * 
     * @throws StateLimitException
     */
    void changeBackTaskManagerPath(boolean isToClearNew) throws StateLimitException;

    /**
     * Redoes update the task manager to the new file path and deletes the data
     * file in the previous file path if it was specified.
     * 
     * @throws StateLimitException
     */
    void redoUpdateTaskManagerPath() throws StateLimitException;

    /** Saves the current state of the task manager. */
    void saveState(String message);

    /**
     * Changes the task manager to the previous state.
     * 
     * @return String message
     * @throws StateLimitException
     */
    String getPreviousState() throws StateLimitException;

    /**
     * Updates the task manager to the next state.
     * 
     * @return String message
     * @throws StateLimitException
     */
    String getNextState() throws StateLimitException;

    /**
     * Updates the filter of the filter task list so it will correspond to
     * changes in the filter panel.
     */
    void handleFilterPanelChangedEvent(FilterPanelChangedEvent abce);

    //@@author A0142325R
    /** Updates the task manager to show all up-to-date tasks. */
    void refreshTask();
}
