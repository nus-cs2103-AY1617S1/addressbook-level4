package seedu.todo.model;

import java.util.Comparator;
import java.util.function.Predicate;

import seedu.todo.commons.core.UnmodifiableObservableList;
import seedu.todo.commons.exceptions.IllegalValueException;
import seedu.todo.model.task.ReadOnlyTask;

public interface TodoModel {
    public void add(ReadOnlyTask task);
    
    public void delete(ReadOnlyTask task) throws IllegalValueException;
    
    public void complete(ReadOnlyTask task) throws IllegalValueException;
    
    public void update(ReadOnlyTask task) throws IllegalValueException;
    
    public void view(Predicate<ReadOnlyTask> filter, Comparator<ReadOnlyTask> comparator);
    
    public UnmodifiableObservableList<ReadOnlyTask> getObserveableList();
}
