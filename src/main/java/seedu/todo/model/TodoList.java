package seedu.todo.model;

import java.util.Collections;
import java.util.Comparator;
import java.util.List;
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
        tasks = FXCollections.observableArrayList();
        filteredTasks = new FilteredList<>(tasks, p -> true);
        sortedTasks = new SortedList<>(filteredTasks);
        pinnedTasks = new SortedList<>(sortedTasks, (a, b) -> Boolean.compare(a.isPinned(), b.isPinned()));
    }

    @Override
    public void add(ReadOnlyTask task) {
        tasks.add(Task.unwrap(task));
    }

    @Override
    public void delete(ReadOnlyTask task) throws IllegalValueException {
        if (!tasks.remove(task)) {
            throw new IllegalValueException("Task not found in todo list");
        } 
    }

    @Override
    public void update(ReadOnlyTask task) throws IllegalValueException {
        int index = tasks.indexOf(task);
        
        if (index == -1) {
            throw new IllegalValueException("Task not found in todo list");
        } else {
            tasks.set(index, Task.unwrap(task));
        }
    }

    @Override
    public void view(Predicate<ReadOnlyTask> filter, Comparator<ReadOnlyTask> comparator) {
        filteredTasks.setPredicate(filter);
        sortedTasks.setComparator(comparator);
    }

    @Override
    public UnmodifiableObservableList<ReadOnlyTask> getObserveableList() {
        return new UnmodifiableObservableList<>(pinnedTasks);
    }

    @Override
    public List<ReadOnlyTask> getTasks() {
        return Collections.unmodifiableList(tasks);
    }
}
