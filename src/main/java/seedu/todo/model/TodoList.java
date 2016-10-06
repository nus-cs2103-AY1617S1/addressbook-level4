package seedu.todo.model;

import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.function.Consumer;
import java.util.function.Predicate;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.collections.transformation.FilteredList;
import javafx.collections.transformation.SortedList;

import seedu.todo.commons.core.UnmodifiableObservableList;
import seedu.todo.commons.exceptions.IllegalValueException;
import seedu.todo.model.task.ImmutableTask;
import seedu.todo.model.task.Task;

public class TodoList implements ImmutableTodoList, TodoModel {
    private ObservableList<Task> tasks = FXCollections.observableArrayList(t -> t.getObservableProperties());
    private FilteredList<Task> filteredTasks = new FilteredList<>(tasks);
    private SortedList<Task> sortedTasks = new SortedList<>(filteredTasks);
    
    public TodoList() {
        // Set the default comparators
        view(null, null);
    }

    @Override
    public void add(String title) {
        tasks.add(new Task(title));
    }
    
    @Override
    public void add(String title, Consumer<Task> update) {
        Task task = new Task(title);
        update.accept(task);
        tasks.add(task);
    }

    @Override
    public void delete(ImmutableTask task) throws IllegalValueException {
        if (!tasks.remove(task)) {
            throw new IllegalValueException("Task not found in todo list");
        } 
    }

    @Override
    public void update(ImmutableTask key, Consumer<Task> update) throws IllegalValueException {
        int index = tasks.indexOf(key);
        
        if (index < 0) {
            throw new IllegalValueException("Task not found in todo list");
        } else {
            Task task = tasks.get(index);
            update.accept(task);
        }
    }

    @Override
    public void view(Predicate<ImmutableTask> filter, Comparator<ImmutableTask> comparator) {
        filteredTasks.setPredicate(filter);
        
        sortedTasks.setComparator((a, b) -> {
            int pin = Boolean.compare(b.isPinned(), a.isPinned());
            return pin != 0 || comparator == null ? pin : comparator.compare(a, b);
        });
    }

    @Override
    public UnmodifiableObservableList<ImmutableTask> getObserveableList() {
        return new UnmodifiableObservableList<>(sortedTasks);
    }

    @Override
    public List<ImmutableTask> getTasks() {
        return Collections.unmodifiableList(tasks);
    }
}
