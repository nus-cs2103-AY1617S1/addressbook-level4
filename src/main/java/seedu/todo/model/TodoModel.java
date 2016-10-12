package seedu.todo.model;

import java.util.Comparator;
import java.util.function.Consumer;
import java.util.function.Predicate;

import seedu.todo.commons.core.UnmodifiableObservableList;
import seedu.todo.commons.exceptions.IllegalValueException;
import seedu.todo.commons.exceptions.ValidationException;
import seedu.todo.model.task.ImmutableTask;
import seedu.todo.model.task.MutableTask;
import seedu.todo.model.task.Task;

/**
 * Represents the data layer of the application.
 */
public interface TodoModel {
    /**
     * Adds a new task or event with title only to the todo list.
     * 
     * @param title  the title of the task 
     * @throws IllegalValueException if the values set in the update predicate is invalid
     */
    public void add(String title) throws IllegalValueException;
    
    /**
     * Adds a new task or event with title and other fields to the todo list.
     * 
     * @param title   the title of the task 
     * @param update  a {@link MutableTask} is passed into this lambda. All other fields 
     *                should be set from inside this lambda. 
     *  
     * @throws IllegalValueException if the values set in the update predicate is invalid
     * @throws ValidationException   if the fields in the task to be updated are not valid
     */
    public void add(String title, Consumer<MutableTask> update) throws IllegalValueException, ValidationException;
    
    /**
     * Deletes the given task from the todo list. This change is also propagated to the 
     * underlying persistence layer.  
     * 
     * @param task  a reference to the task that needs to be deleted
     * 
     * @throws IllegalValueException if the task does not exist
     */
    public void delete(ImmutableTask task) throws IllegalValueException;
    
    /**
     * Replaces certain fields in the task. Mutation of the {@link Task} object should 
     * only be done in the <code>update</code> lambda. The lambda takes in one parameter, 
     * a {@link MutableTask}, and does not expect any return value. For example: 
     * 
     * <pre><code>todo.update(task, t -> {
     *     t.setEndTime(t.getEndTime.get().plusHours(2)); // Push deadline back by 2h
     *     t.setPin(true); // Pin this task
     * });</code></pre>
     * 
     * @throws IllegalValueException  if the task does not exist or if the values set in the 
     *                                update predicate is invalid
     * @throws ValidationException    if the fields in the task to be updated are not valid
     */
    public void update(ImmutableTask task, Consumer<MutableTask> update) throws IllegalValueException, ValidationException;
    
    /**
     * Changes the filter predicate and sort comparator used to display the tasks. A null
     * value for either parameter resets it to the default value - showing everything for 
     * the filter and insertion order for sort. 
     */
    public void view(Predicate<ImmutableTask> filter, Comparator<ImmutableTask> sort);
    
    /**
     * Get an observable list of tasks. Used mainly by the JavaFX UI. 
     */
    public UnmodifiableObservableList<ImmutableTask> getObserveableList();
}
