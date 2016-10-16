package seedu.todo.model;

import java.io.FileNotFoundException;
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
import seedu.todo.commons.core.EventsCenter;
import seedu.todo.commons.core.LogsCenter;
import seedu.todo.commons.core.UnmodifiableObservableList;
import seedu.todo.commons.events.storage.DataSavingExceptionEvent;
import seedu.todo.commons.exceptions.DataConversionException;
import seedu.todo.commons.exceptions.ValidationException;
import seedu.todo.model.task.ImmutableTask;
import seedu.todo.model.task.MutableTask;
import seedu.todo.model.task.Task;
import seedu.todo.model.task.ValidationTask;
import seedu.todo.storage.MoveableStorage;

/**
 * Represents the data layer of the application. Implements TodoModel interface so that it can provide
 * data to other parts of the logic layer, and the ImmutableTodoList interface so that it can be
 * serialized and persisted directly
 */
public class TodoList implements ImmutableTodoList, TodoModel {
    private static final String INDEX_OUT_OF_BOUND_FORMAT = "There is no task no. %d";
    private static final String INCORRECT_FILE_FORMAT_ERROR = "The file doesn't seem to be in the correct format.";
    private static final String FILE_NOT_FOUND_FORMAT = "%s does not seem to exist.";
    
    private ObservableList<Task> tasks = FXCollections.observableArrayList(Task::getObservableProperties);
    private FilteredList<Task> filteredTasks = new FilteredList<>(tasks);
    private SortedList<Task> sortedTasks = new SortedList<>(filteredTasks);

    private MoveableStorage<ImmutableTodoList> storage;

    private static final Logger logger = LogsCenter.getLogger(TodoList.class);
    private static final EventsCenter events = EventsCenter.getInstance();

    public TodoList(MoveableStorage<ImmutableTodoList> storage) {
        this.storage = storage;
        Optional<ImmutableTodoList> todoListOptional; 
        
        try {
            todoListOptional = storage.read();
        } catch (DataConversionException e) {
            raiseStorageEvent(TodoList.INCORRECT_FILE_FORMAT_ERROR, e);
            todoListOptional = Optional.empty();
        } catch (FileNotFoundException e) {
            todoListOptional = Optional.empty();
        }

        if (todoListOptional.isPresent()) {
            initTodoList(todoListOptional.get());
        } else {
            logger.info("Data file not found. Will be starting with an empty TodoList");
        }

        // Set the default comparators
        view(null, null);
    }
    
    private void raiseStorageEvent(String message, Exception e) {
        // TODO: Have this raise an event 
    }

    private void initTodoList(ImmutableTodoList initialData) {
        tasks.setAll(initialData.getTasks().stream().map(Task::new).collect(Collectors.toList()));
    }
    
    private void saveTodoList() {
        try {
            storage.save(this);
        } catch (IOException e) {
            events.post(new DataSavingExceptionEvent(e));
        }
    }
    
    private int getTaskIndex(int index) throws ValidationException {
        int taskIndex; 
        
        try {
            ImmutableTask task = getObserveableList().get(index - 1);
            taskIndex = tasks.indexOf(task);
        } catch (IndexOutOfBoundsException e) {
            taskIndex = -1;
        }
        
        if (taskIndex == -1) {
            String message = String.format(TodoList.INDEX_OUT_OF_BOUND_FORMAT, index);
            throw new ValidationException(message, new ErrorBag());
        }
        
        return taskIndex;
    }

    @Override
    public ImmutableTask add(String title) {
        Task task = new Task(title);
        tasks.add(task);

        try {
            storage.save(this);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return task;
    }

    @Override
    public ImmutableTask add(String title, Consumer<MutableTask> update) throws ValidationException {
        ValidationTask validationTask = new ValidationTask(title);
        update.accept(validationTask);
        Task task = validationTask.convertToTask();
        tasks.add(task);

        saveTodoList();
        return task;
    }

    @Override
    public ImmutableTask delete(int index) throws ValidationException {
        Task task;
        task = tasks.remove(getTaskIndex(index));
        saveTodoList();
        return task;
    }

    @Override
    public ImmutableTask update(int index, Consumer<MutableTask> update) throws ValidationException {
        Task task;
        task = tasks.get(getTaskIndex(index));

        ValidationTask validationTask = new ValidationTask(task);
        update.accept(validationTask);
        validationTask.validate();

        // changes are validated and accepted
        update.accept(task);
        saveTodoList();
        return task;
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
    public void save(String location) {
        try {
            storage.save(this, location);
        } catch (IOException e) {
            
        }
    }

    @Override
    public void load(String location) throws ValidationException {
        Optional<ImmutableTodoList> todoList = null;
        try {
            todoList = storage.read(location);
        } catch (DataConversionException e) {
            throw new ValidationException(TodoList.INCORRECT_FILE_FORMAT_ERROR);
        } catch (FileNotFoundException e) {
            String message = String.format(TodoList.FILE_NOT_FOUND_FORMAT, location);
            throw new ValidationException(message);
        }
        
        if (todoList.isPresent()) {
            initTodoList(todoList.get());
        } else {
            throw new ValidationException("Something went wrong while loading the data");
        }
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
