package seedu.tasklist.model;

import seedu.tasklist.commons.core.UnmodifiableObservableList;
import seedu.tasklist.model.task.ReadOnlyTask;
import seedu.tasklist.model.task.Task;
import seedu.tasklist.model.task.UniqueTaskList;
import seedu.tasklist.model.task.UniqueTaskList.TaskCompletionException;
import seedu.tasklist.model.task.UniqueTaskList.TaskNotFoundException;

import java.util.Set;

/**
 * The API of the Model component.
 */
public interface Model {
    /** Clears existing backing model and replaces with the provided new data. */
    void resetData(ReadOnlyTaskList newData);

    /** Returns the TaskList */
    ReadOnlyTaskList getTaskList();

    /** Deletes the given task. */
    void deleteTask(ReadOnlyTask target) throws UniqueTaskList.TaskNotFoundException;

    /** Edits the given task. */
    void editTask(Task taskToEdit, ReadOnlyTask target) throws TaskNotFoundException ;

    /** Adds the given task */
    void addTask(Task task) throws UniqueTaskList.DuplicateTaskException;
    
    /** Marks the given task */
    void markTask(ReadOnlyTask target) throws TaskNotFoundException, TaskCompletionException;
    
    /** Unmarks the given task */
    void unmarkTask(ReadOnlyTask target) throws TaskNotFoundException, TaskCompletionException;

    /** Returns the filtered task list as an {@code UnmodifiableObservableList<ReadOnlyTask>} */
    UnmodifiableObservableList<ReadOnlyTask> getFilteredTaskList();

    /** Updates the filter of the filtered task list to show all tasks */
    void updateFilteredListToShowAll();

    /** Updates the filter of the filtered task list to filter by the given keywords*/
    void updateFilteredTaskList(Set<String> keywords);

    /** Returns the filtered task list for list command as an {@code UnmodifiableObservableList<ReadOnlyTask>} */
    UnmodifiableObservableList<ReadOnlyTask> getListCommandFilteredTaskList();

    /** Updates the task list after change file path*/
    void updateFilePathChange();
    
    /** Gives the time remaining before a deadline/ an event*/
    String timeTask(Task target) throws UniqueTaskList.TaskNotFoundException;

}
