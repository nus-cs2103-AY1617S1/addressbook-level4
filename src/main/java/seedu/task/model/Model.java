package seedu.task.model;

import java.util.Set;

import seedu.task.commons.core.UnmodifiableObservableList;
import seedu.task.model.task.ReadOnlyTask;
import seedu.task.model.task.Task;
import seedu.task.model.task.UniqueTaskList;

/**
 * The API of the Model component.
 */
public interface Model {
    /**
     * Clears existing backing model and replaces with the provided new data.
     */
    void resetData(ReadOnlyTaskManager newData);

    /** Returns the Task List */
    ReadOnlyTaskManager getTaskManager();

    /** Deletes the given task. */
    void deleteTask(ReadOnlyTask target) throws UniqueTaskList.TaskNotFoundException;

    /** Adds the given task */
    void addTask(Task task) throws UniqueTaskList.DuplicateTaskException;

    /** Updates the given task */
    void updateTask(ReadOnlyTask orginalTask, Task updateTask) throws UniqueTaskList.DuplicateTaskException;
    
    /** Rollback the task list */
    void rollback();
    
    /** Pins the given task as important */
    void pinTask(ReadOnlyTask originalTask, Task toPin);

    //@@author A0153467Y    
    /** Mark the given task as completed */
    void completeTask(ReadOnlyTask originalTask, Task completedTask);
    
    //@@author A0153467Y
    /** Unmark the given important task */
    void uncompleteTask(ReadOnlyTask originalTask, Task uncompletedTask);
    //@@author
    /** Returns the filtered task list as an {@code UnmodifiableObservableList<ReadOnlyTask>} */
    UnmodifiableObservableList<ReadOnlyTask> getFilteredTaskList();

    /** Updates the filter of the filtered task list to show all tasks */
    void updateFilteredListToShowAll();

    /**
     * Updates the filter of the filtered task list to filter by the given
     * keywords
     */
    void updateFilteredTaskList(Set<String> keywords);

}
