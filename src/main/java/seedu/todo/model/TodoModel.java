package seedu.todo.model;

import java.util.Comparator;
import java.util.function.Predicate;

import seedu.todo.commons.core.UnmodifiableObservableList;
import seedu.todo.commons.exceptions.IllegalValueException;
import seedu.todo.model.task.ReadOnlyTask;
import seedu.todo.model.task.Task;

public interface TodoModel {
    public void add(Task task);
    
    public void delete(ReadOnlyTask task) throws IllegalValueException;
    
    public void update(Task task) throws IllegalValueException;
    
    public void view(Predicate<ReadOnlyTask> filter, Comparator<ReadOnlyTask> comparator);
    
    public UnmodifiableObservableList<ReadOnlyTask> getObserveableList();
}
