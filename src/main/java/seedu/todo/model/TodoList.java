package seedu.todo.model;

import java.io.IOException;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.Optional;
import java.util.function.Consumer;
import java.util.function.Predicate;
import java.util.logging.Logger;
import java.util.stream.Collectors;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.collections.transformation.FilteredList;
import javafx.collections.transformation.SortedList;

import seedu.todo.commons.core.LogsCenter;
import seedu.todo.commons.core.UnmodifiableObservableList;
import seedu.todo.commons.exceptions.DataConversionException;
import seedu.todo.commons.exceptions.IllegalValueException;
import seedu.todo.model.task.ImmutableTask;
import seedu.todo.model.task.Task;
import seedu.todo.storage.TodoListStorage;

public class TodoList implements ImmutableTodoList, TodoModel {
    private ObservableList<Task> tasks = FXCollections.observableArrayList(t -> t.getObservableProperties());
    private FilteredList<Task> filteredTasks = new FilteredList<>(tasks);
    private SortedList<Task> sortedTasks = new SortedList<>(filteredTasks);
    
    private static final Logger logger = LogsCenter.getLogger(TodoList.class);
    
    public TodoList(TodoListStorage storage) {
        try {
            Optional<ImmutableTodoList> todoListOptional = storage.readTodoList();
            if (todoListOptional.isPresent()){
                initTodoList(todoListOptional.get());
            } else {
                logger.info("Data file not found. Will be starting with an empty TodoList");
            }
        } catch (DataConversionException e) {
            logger.warning("Data file not in the correct format. Will be starting with an empty TodoList");
        } catch (IOException e) {
            logger.warning("Problem while reading from the file. Will be starting with an empty TodoList");
        }

        // Set the default comparators
        view(null, null);
    }
    
    private void initTodoList(ImmutableTodoList initialData) {
        tasks.setAll(initialData.getTasks().stream().map(Task::new).collect(Collectors.toList()));
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
