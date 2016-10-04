package seedu.todo.model;

import java.util.Comparator;
import java.util.function.Consumer;
import java.util.function.Predicate;

import seedu.todo.commons.core.UnmodifiableObservableList;
import seedu.todo.commons.exceptions.IllegalValueException;
import seedu.todo.model.task.ReadOnlyTask;
import seedu.todo.model.task.Task;

public interface TodoModel {
    /**
     * Adds a new task to the todo list
     */
    public void add(String title);
    public void add(String title, Consumer<Task> update);
    
    /**
     * Deletes the given task 
     * @throws IllegalValueException if the task does not exist
     */
    public void delete(ReadOnlyTask task) throws IllegalValueException;
    
    /**
     * Replace the provided task with the given task
     * @throws IllegalValueException if the task does not exist
     */
    public void update(ReadOnlyTask task, Consumer<Task> update) throws IllegalValueException;
    
    /**
     * Update the filter predicate and sort comparator used to display the tasks
     */
    public void view(Predicate<ReadOnlyTask> filter, Comparator<ReadOnlyTask> comparator);
    
    /**
     * Get an unmodifiable observable list of tasks. Used mainly by the UI. 
     */
    public UnmodifiableObservableList<ReadOnlyTask> getObserveableList();
}
