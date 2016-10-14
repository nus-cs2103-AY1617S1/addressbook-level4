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
    private static final String INDEX_OUT_OF_BOUND_ERROR = "Task not found in task list";
    
    private ObservableList<Task> tasks = FXCollections.observableArrayList(Task::getObservableProperties);
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
    
    private void outOfBoundError() throws ValidationException {
        ErrorBag e = new ErrorBag();
        e.put("index", TodoList.INDEX_OUT_OF_BOUND_ERROR);
        e.validate("There was a problem with your command");
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

        tasks.add(validationTask.convertToTask());

        storage.saveTodoList(this);
    }

    @Override
    public void delete(int index) throws ValidationException {
        try {
            tasks.remove(index - 1);
        } catch (ArrayIndexOutOfBoundsException e) {
            outOfBoundError();
            return;
        }

        storage.saveTodoList(this);
    }

    @Override
    public void update(int index, Consumer<MutableTask> update) throws ValidationException {
        Task task; 
        
        try {
            task = tasks.get(index - 1);
        } catch (ArrayIndexOutOfBoundsException e) {
            outOfBoundError();
            return;
        }

        ValidationTask validationTask = new ValidationTask(task);
        update.accept(validationTask);
        validationTask.validate();

        // changes are validated and accepted
        update.accept(task);
        storage.saveTodoList(this);
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
