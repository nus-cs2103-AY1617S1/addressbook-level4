package seedu.todo.model;

import java.util.Comparator;
import java.util.function.Consumer;
import java.util.function.Predicate;

import seedu.todo.commons.core.UnmodifiableObservableList;
import seedu.todo.commons.exceptions.IllegalValueException;
import seedu.todo.model.task.ReadOnlyTask;
import seedu.todo.model.task.Task;

/**
 * Represents the data layer of the application.
 *
 */
public interface TodoModel {
    /**
     * Adds a new task to the todo list. To set additional fields, use the 
     * <code>update</code> lambda which takes in one parameter, a mutable Task
     * and does not expect any return value.  
     * 
     * @throws IllegalValueException if the values set in the update predicate is invalid
     */
    public void add(String title) throws IllegalValueException;
    public void add(String title, Consumer<Task> update)  throws IllegalValueException;
    
    /**
     * Deletes the given task 
     * 
     * @throws IllegalValueException if the task does not exist
     */
    public void delete(ReadOnlyTask task) throws IllegalValueException;
    
    /**
     * Updates the provided task. Mutation of the Task object should only be done in the 
     * <code>update</code> lambda. The lambda takes in one parameter, a mutable Task, 
     * and does not expect any return value. For example: 
     * 
     * <pre><code>todo.update(task, t -> {
     *     t.setEndTime(t.getEndTime.get().plusHours(2)); // Push deadline back by 2h
     *     t.setPin(true);
     * });</code></pre>
     * 
     * @throws IllegalValueException if the task does not exist or if the values set in the 
     * update predicate is invalid
     */
    public void update(ReadOnlyTask task, Consumer<Task> update) throws IllegalValueException;
    
    /**
     * Update the filter predicate and sort comparator used to display the tasks. A null
     * value for either parameter resets it to the default value - showing everything for 
     * the filter and insertion order for sort. 
     */
    public void view(Predicate<ReadOnlyTask> filter, Comparator<ReadOnlyTask> sort);
    
    /**
     * Get an observable list of tasks. Used mainly by the JavaFX UI. 
     */
    public UnmodifiableObservableList<ReadOnlyTask> getObserveableList();
}
