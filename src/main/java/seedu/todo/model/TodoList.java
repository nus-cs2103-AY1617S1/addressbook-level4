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
import seedu.todo.model.task.ReadOnlyTask;
import seedu.todo.model.task.Task;

public class TodoList implements ReadOnlyTodoList, TodoModel {
    private ObservableList<Task> tasks;
    private FilteredList<Task> filteredTasks;
    private SortedList<Task> sortedTasks;
    private SortedList<Task> pinnedTasks;
    
    public TodoList() {
        tasks = FXCollections.observableArrayList(t -> t.getObservableProperties());
        filteredTasks = new FilteredList<>(tasks, p -> true);
        sortedTasks = new SortedList<>(filteredTasks);
        // pinnedTasks = new SortedList<>(sortedTasks, (a, b) -> Boolean.compare(a.isPinned(), b.isPinned()));
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
    public void delete(ReadOnlyTask task) throws IllegalValueException {
        if (!tasks.remove(task)) {
            throw new IllegalValueException("Task not found in todo list");
        } 
    }

    @Override
    public void update(ReadOnlyTask key, Consumer<Task> update) throws IllegalValueException {
        int index = tasks.indexOf(key);
        
        if (index < 0) {
            throw new IllegalValueException("Task not found in todo list");
        } else {
            Task task = tasks.get(index);
            update.accept(task);
        }
    }

    @Override
    public void view(Predicate<ReadOnlyTask> filter, Comparator<ReadOnlyTask> comparator) {
        filteredTasks.setPredicate(filter);
        if (comparator == null) {
            sortedTasks.setComparator(comparator);
        } else {
            sortedTasks.setComparator((a, b) -> {
                System.out.printf("a: %s %b / b: %s %b\n", a.getTitle(), a.isPinned(), b.getTitle(), b.isPinned());
                int pin = Boolean.compare(b.isPinned(), a.isPinned());
                return pin != 0 ? pin : comparator.compare(a, b);
            });
        }
    }

    @Override
    public UnmodifiableObservableList<ReadOnlyTask> getObserveableList() {
        return new UnmodifiableObservableList<>(sortedTasks);
    }

    @Override
    public List<ReadOnlyTask> getTasks() {
        return Collections.unmodifiableList(tasks);
    }
}
