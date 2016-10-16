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
     * @return the task that was just created 
     * @throws IllegalValueException if the values set in the update predicate is invalid
     */
    public ImmutableTask add(String title) throws IllegalValueException;
    
    /**
     * Adds a new task or event with title and other fields to the todo list.
     * 
     * @param title   the title of the task 
     * @param update  a {@link MutableTask} is passed into this lambda. All other fields 
     *                should be set from inside this lambda. 
     * @return the task that was just created
     * @throws ValidationException   if the fields in the task to be updated are not valid
     */
    public ImmutableTask add(String title, Consumer<MutableTask> update) throws ValidationException;
    
    /**
     * Deletes the given task from the todo list. This change is also propagated to the 
     * underlying persistence layer.  
     * 
     * @param index  the 1-indexed position of the task that needs to be deleted
     * @return the task that was just deleted
     * @throws ValidationException if the task does not exist
     */
    public ImmutableTask delete(int index) throws ValidationException;
    
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
     * @return the task that was just updated
     * 
     * @throws ValidationException    if the task does not exist or if the fields in the 
     *                                task to be updated are not valid
     */
    public ImmutableTask update(int index, Consumer<MutableTask> update) throws ValidationException;
    
    /**
     * Changes the filter predicate and sort comparator used to display the tasks. A null
     * value for either parameter resets it to the default value - showing everything for 
     * the filter and insertion order for sort. 
     */
    public void view(Predicate<ImmutableTask> filter, Comparator<ImmutableTask> sort);

    /**
     * Changes the save path of the TodoList storage 
     * @throws ValidationException if the path is not valid
     */
    public void save(String location) throws ValidationException;

    /**
     * Loads a TodoList from the path. 
     * @throws ValidationException if the path or file is invalid
     */
    public void load(String location) throws ValidationException;
    
    /**
     * Get an observable list of tasks. Used mainly by the JavaFX UI. 
     */
    public UnmodifiableObservableList<ImmutableTask> getObserveableList();
}
