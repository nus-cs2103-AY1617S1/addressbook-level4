package seedu.task.model;

import seedu.task.commons.core.UnmodifiableObservableList;
import seedu.task.model.task.ReadOnlyTask;
import seedu.task.model.task.Task;
import seedu.task.model.task.UniqueTaskList;

import java.util.Set;

/**
 * The API of the Model component.
 * @@author A0147335E-reused
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
    
    /** Adds the given task on a specific index */
    void addTask(int index, Task task) throws UniqueTaskList.DuplicateTaskException;

    /** Returns the filtered task list as an {@code UnmodifiableObservableList<ReadOnlyTask>} */
    UnmodifiableObservableList<ReadOnlyTask> getFilteredTaskList();

    /** Updates the filter of the filtered task list to show all tasks */
    void updateFilteredListToShowAll();

    /** Updates the filter of the filtered task list to filter by the given keywords*/
    void updateFilteredTaskList(Set<String> keywords);
    
    //@@author A0147944U
    /** Updates the sort comparator of the sorted task list to sort by the given comparator*/
    void sortFilteredTaskList(String keyword);
    
    /** Updates sorting method in config based on keyword*/
    void saveCurrentSortPreference(String keyword);
    
    /** Automatically sorts tasks based on current sort preferences in config*/
    public void autoSortBasedOnCurrentSortPreference();
    
    /** asdasd*/
    public void repeatRecurringTask(Task recurringTask);
    //@@author

}
