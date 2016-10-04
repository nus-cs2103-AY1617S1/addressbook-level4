package seedu.todo.model;

import java.util.Comparator;
import java.util.function.Predicate;

import seedu.todo.commons.core.UnmodifiableObservableList;
import seedu.todo.commons.exceptions.IllegalValueException;
import seedu.todo.model.task.ReadOnlyTask;

public interface TodoModel {
    /**
     * Adds a new task to the todo list
     */
    public void add(ReadOnlyTask task);
    
    /**
     * Deletes the given task 
     * @throws IllegalValueException if the task does not exist
     */
    public void delete(ReadOnlyTask task) throws IllegalValueException;
    
    /**
     * Replace the provided task with the given task
     * @throws IllegalValueException if the task does not exist
     */
    public void update(ReadOnlyTask task) throws IllegalValueException;
    
    /**
     * Update the filter predicate and sort comparator used to display the tasks
     */
    public void view(Predicate<ReadOnlyTask> filter, Comparator<ReadOnlyTask> comparator);
    
    /**
     * Get an unmodifiable observable list of tasks. Used mainly by the UI. 
     */
    public UnmodifiableObservableList<ReadOnlyTask> getObserveableList();
}
