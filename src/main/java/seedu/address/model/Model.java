package seedu.address.model;

import seedu.address.commons.core.UnmodifiableObservableList;
import seedu.address.commons.core.ModifiableObservableList;
import seedu.address.model.task.ReadOnlyTask;
import seedu.address.model.task.Task;
import seedu.address.model.task.UniqueTaskList;


import java.util.Set;
import java.time.LocalDate;

/**
 * The API of the Model component.
 */
public interface Model {
    /** Clears existing backing model and replaces with the provided new data. */
    void resetData(ReadOnlyToDoList newData);

    /** Returns the ToDoList */
    ReadOnlyToDoList getToDoList();

    /** Deletes the given task. */
    void deleteTask(ReadOnlyTask target) throws UniqueTaskList.TaskNotFoundException;

    /** Retrieve the give task. */
    Task getTask(ReadOnlyTask target) throws UniqueTaskList.TaskNotFoundException;
    
    /** Update the given task */
    void addTask(Task task) throws UniqueTaskList.DuplicateTaskException;
    
    /** Adds the given task */
    void updateTask(ReadOnlyTask oldTask, ReadOnlyTask newTask) throws UniqueTaskList.TaskNotFoundException;

    /** Returns the filtered person list as an {@code ModifiableObservableList<Task>} */
    ModifiableObservableList<Task> getFilteredTaskList();
    
    /** Returns the filtered person list as an {@code UnmodifiableObservableList<ReadOnlyTask>} */
    UnmodifiableObservableList<ReadOnlyTask> getUnmodifiableFilteredTaskList();


    /** Updates the filter of the filtered task list to show all tasks */
    void updateFilteredListToShowAll();

    /** Updates the filter of the filtered task list to filter by the given keywords*/
    void updateFilteredTaskList(Set<String> keywords);
    
    /** Updates the filter of the filtered task list to filter by the given keywords*/
    void updateFilteredTaskList(LocalDate date);

}
