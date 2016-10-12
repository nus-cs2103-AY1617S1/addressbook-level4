package seedu.todo.model;

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
import seedu.todo.commons.exceptions.IllegalValueException;
import seedu.todo.commons.exceptions.ValidationException;
import seedu.todo.model.task.ImmutableTask;
import seedu.todo.model.task.MutableTask;
import seedu.todo.model.task.Task;
import seedu.todo.model.task.ValidationTask;
import seedu.todo.storage.Storage;

/**
 * Represents the data layer of the application. Implements TodoModel interface so that it can provide
 * data to other parts of the logic layer, and the ImmutableTodoList interface so that it can be
 * serialized and persisted directly
 */
public class TodoList implements ImmutableTodoList, TodoModel {
    private ObservableList<Task> tasks = FXCollections.observableArrayList(t -> t.getObservableProperties());
    private FilteredList<Task> filteredTasks = new FilteredList<>(tasks);
    private SortedList<Task> sortedTasks = new SortedList<>(filteredTasks);

    private Storage storage;

    private static final Logger logger = LogsCenter.getLogger(TodoList.class);

    public TodoList(Storage storage) {
        this.storage = storage;

        Optional<ImmutableTodoList> todoListOptional = storage.readTodoList();

        if (todoListOptional.isPresent()) {
            initTodoList(todoListOptional.get());
        } else {
            logger.info("Data file not found. Will be starting with an empty TodoList");
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

        storage.saveTodoList(this);
    }

    @Override
    public void add(String title, Consumer<MutableTask> update) throws ValidationException {
        ValidationTask validationTask = new ValidationTask(title);
        update.accept(validationTask);
        validationTask.validate();

        tasks.add(validationTask.convertToTask());

        storage.saveTodoList(this);
    }

    @Override
    public void delete(ImmutableTask task) throws IllegalValueException {
        if (!tasks.remove(task)) {
            throw new IllegalValueException("Task not found in todo list");
        }

        storage.saveTodoList(this);
    }

    @Override
    public void update(ImmutableTask key, Consumer<MutableTask> update) throws IllegalValueException, ValidationException {
        int index = tasks.indexOf(key);

        if (index < 0) {
            throw new IllegalValueException("Task not found in todo list");
        } else {
            Task task = tasks.get(index);
            ValidationTask validationTask = new ValidationTask(task);
            update.accept(validationTask);
            validationTask.validate();

            // changes are validated and accepted
            update.accept(task);
            storage.saveTodoList(this);
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
