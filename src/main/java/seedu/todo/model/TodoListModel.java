package seedu.todo.model;

import java.util.List;
import java.util.function.Consumer;

import javafx.collections.ObservableList;
import seedu.todo.commons.exceptions.IllegalValueException;
import seedu.todo.commons.exceptions.ValidationException;
import seedu.todo.model.task.ImmutableTask;
import seedu.todo.model.task.MutableTask;
import seedu.todo.model.task.Task;

//@@author A0135817B
public interface TodoListModel extends ImmutableTodoList {
    /**
     * Adds a new task or event with title only to the todo list.
     *
     * @param title  the title of the task
     * @return the task that was just created 
     * @throws IllegalValueException if the values set in the update predicate is invalid
     */
    ImmutableTask add(String title) throws IllegalValueException;

    /**
     * Adds a new task or event with title and other fields to the todo list.
     *
     * @param title   the title of the task 
     * @param update  a {@link MutableTask} is passed into this lambda. All other fields 
     *                should be set from inside this lambda. 
     * @return the task that was just created
     * @throws ValidationException   if the fields in the task to be updated are not valid
     */
    ImmutableTask add(String title, Consumer<MutableTask> update) throws ValidationException;

    /**
     * Deletes the given task from the todo list. This change is also propagated to the 
     * underlying persistence layer.  
     *
     * @param index  the 1-indexed position of the task that needs to be deleted
     * @return the task that was just deleted
     * @throws ValidationException if the task does not exist
     */
    ImmutableTask delete(int index) throws ValidationException;

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
    ImmutableTask update(int index, Consumer<MutableTask> update) throws ValidationException;
    
    //@@author A0092382A
    /**
     * Carries out the specified update in the fields of all visible tasks. Mutation of all {@link Task}
     * objects should only be done in the <code>update</code> lambda. The lambda takes in a single parameter,
     * a {@link MutableTask}, and does not expect any return value, as per the {@link update} command. Note that
     * the 'All' in this case refers to all the indices specified by the accompanying list of indices. 
     * 
     * <pre><code>todo.updateAll (List<Integer> tasks, t -> {
     *     t.setEndTime(t.getEndTime.get().plusHours(2)); // Push deadline of all specified tasks back by 2h
     *     t.setPin(true); // Pin all tasks specified
     * });</code></pre>
     * 
     * @throws ValidationException if any updates on any of the task objects are considered invalid
     */
    void updateAll(List<Integer> indexes, Consumer <MutableTask> update) throws ValidationException;

    //@@author A0135817B
    /**
     * Changes the save path of the TodoList storage 
     * @throws ValidationException if the path is not valid
     */
    void save(String location) throws ValidationException;

    /**
     * Loads a TodoList from the path. 
     * @throws ValidationException if the path or file is invalid
     */
    void load(String location) throws ValidationException;

    /**
     * Replaces the tasks in list with the one in the 
     */
    void setTasks(List<ImmutableTask> todoList);

    /**
     * Get an observable list of tasks. Used mainly by the JavaFX UI. 
     */
    ObservableList<ImmutableTask> getObservableList();

}

