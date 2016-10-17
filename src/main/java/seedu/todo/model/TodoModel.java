package seedu.todo.model;

import javafx.collections.ObservableList;
import javafx.collections.transformation.FilteredList;
import javafx.collections.transformation.SortedList;
import seedu.todo.commons.core.Config;
import seedu.todo.commons.core.UnmodifiableObservableList;
import seedu.todo.commons.exceptions.IllegalValueException;
import seedu.todo.commons.exceptions.ValidationException;
import seedu.todo.model.task.ImmutableTask;
import seedu.todo.model.task.MutableTask;
import seedu.todo.storage.MoveableStorage;
import seedu.todo.storage.TodoListStorage;

import java.util.Comparator;
import java.util.function.Consumer;
import java.util.function.Predicate;

/**
 * Represents the data layer of the application. The TodoModel handles any 
 * interaction with the application state that are not persisted, such as the
 * view (sort and filtering), undo and redo. Since this layer handles 
 * sorting and filtering, task ID must be passed through {@link #getTaskIndex}
 * to transform them into the index {@link TodoList} methods can use. 
 */
public class TodoModel implements Model {
    private static final String INDEX_OUT_OF_BOUND_FORMAT = "There is no task no. %d";

    private TodoListModel todolist;
    private MoveableStorage<ImmutableTodoList> storage;
    
    private ObservableList<ImmutableTask> tasks;
    private FilteredList<ImmutableTask> filteredTasks;
    private SortedList<ImmutableTask> sortedTasks;
    
    public TodoModel(Config config) {
        this(new TodoListStorage(config.getTodoListFilePath()));
    }
    
    public TodoModel(MoveableStorage<ImmutableTodoList> storage) {
        this(new TodoList(storage), storage);
    }
    
    public TodoModel(TodoListModel todolist, MoveableStorage<ImmutableTodoList> storage) {
        this.storage = storage;
        this.todolist = todolist;

        tasks = todolist.getObservableList();
        filteredTasks = new FilteredList<>(tasks);
        sortedTasks = new SortedList<>(filteredTasks);

        view(null, null);
    }

    private int getTaskIndex(int index) throws ValidationException {
        int taskIndex;

        try {
            ImmutableTask task = getObservableList().get(index - 1);
            taskIndex = tasks.indexOf(task);
        } catch (IndexOutOfBoundsException e) {
            taskIndex = -1;
        }

        if (taskIndex == -1) {
            String message = String.format(TodoModel.INDEX_OUT_OF_BOUND_FORMAT, index);
            throw new ValidationException(message);
        }

        return taskIndex;
    }
    
    @Override
    public ImmutableTask add(String title) throws IllegalValueException {
        return todolist.add(title);
    }

    @Override
    public ImmutableTask add(String title, Consumer<MutableTask> update) throws ValidationException {
        return todolist.add(title, update);
    }

    @Override
    public ImmutableTask delete(int index) throws ValidationException {
        return todolist.delete(getTaskIndex(index));
    }

    @Override
    public ImmutableTask update(int index, Consumer<MutableTask> update) throws ValidationException {
        return todolist.update(getTaskIndex(index), update);
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
    public void save(String location) throws ValidationException {
        todolist.save(location);
    }

    @Override
    public void load(String location) throws ValidationException {
        todolist.load(location);
    }

    @Override
    public String getStorageLocation() {
        return storage.getLocation();
    }

    @Override
    public UnmodifiableObservableList<ImmutableTask> getObservableList() {
        return new UnmodifiableObservableList(sortedTasks);
    }
}
