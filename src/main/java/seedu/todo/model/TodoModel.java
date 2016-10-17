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
import seedu.todo.model.task.Task;
import seedu.todo.storage.MoveableStorage;
import seedu.todo.storage.TodoListStorage;

import java.util.ArrayDeque;
import java.util.Comparator;
import java.util.Deque;
import java.util.List;
import java.util.function.Consumer;
import java.util.function.Predicate;
import java.util.stream.Collectors;

/**
 * Represents the data layer of the application. The TodoModel handles any 
 * interaction with the application state that are not persisted, such as the
 * view (sort and filtering), undo and redo. Since this layer handles 
 * sorting and filtering, task ID must be passed through {@link #getTaskIndex}
 * to transform them into the index {@link TodoList} methods can use. 
 */
public class TodoModel implements Model {
    private static final int UNDO_LIMIT = 10;
    
    private static final String INDEX_OUT_OF_BOUND_FORMAT = "There is no task no. %d";
    private static final String NO_MORE_UNDO_REDO_FORMAT = "There are no more steps to %s";

    private TodoListModel todolist;
    private MoveableStorage<ImmutableTodoList> storage;
    
    private ObservableList<ImmutableTask> tasks;
    private FilteredList<ImmutableTask> filteredTasks;
    private SortedList<ImmutableTask> sortedTasks;
    
    private Deque<List<ImmutableTask>> undoStack = new ArrayDeque<>();
    private Deque<List<ImmutableTask>> redoStack = new ArrayDeque<>();
    
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
        
        // Sets the default view 
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

    /**
     * Helper function to manage the undo/redo stacks. Removes the top element from 
     * the first deque, adds it to the second, keeps the second within the limit by 
     * removing from the end any excess element, and returning the element that was 
     * transferred between the stacks. The first stack should not be empty.
     */
    private <T> T popPush(Deque<T> from, Deque<T> to, int limit) {
        T popped = from.removeFirst();
        push(to, popped, limit);
        return popped;
    }
    
    private <T> void push(Deque<T> deque, T element, int limit) {
        deque.addFirst(element);
        while (deque.size() > limit) {
            deque.removeLast();
        }
    }
    
    private void saveUndoState() {
        List<ImmutableTask> tasks = todolist.getTasks().stream()
            .map(Task::new).collect(Collectors.toList());
        push(undoStack, tasks, TodoModel.UNDO_LIMIT);
    }
    
    @Override
    public ImmutableTask add(String title) throws IllegalValueException {
        saveUndoState();
        return todolist.add(title);
    }

    @Override
    public ImmutableTask add(String title, Consumer<MutableTask> update) throws ValidationException {
        saveUndoState();
        return todolist.add(title, update);
    }

    @Override
    public ImmutableTask delete(int index) throws ValidationException {
        saveUndoState();
        return todolist.delete(getTaskIndex(index));
    }

    @Override
    public ImmutableTask update(int index, Consumer<MutableTask> update) throws ValidationException {
        saveUndoState();
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
    public void undo() throws ValidationException {
        if (undoStack.isEmpty()) {
            String message = String.format(TodoModel.NO_MORE_UNDO_REDO_FORMAT, "undo");
            throw new ValidationException(message);
        }

        List<ImmutableTask> tasks = popPush(undoStack, redoStack, TodoModel.UNDO_LIMIT);
        todolist.setTasks(tasks);
    }

    @Override
    public void redo() throws ValidationException {
        if (redoStack.isEmpty()) {
            String message = String.format(TodoModel.NO_MORE_UNDO_REDO_FORMAT, "redo");
            throw new ValidationException(message);
        }

        List<ImmutableTask> tasks = popPush(redoStack, undoStack, TodoModel.UNDO_LIMIT);
        todolist.setTasks(tasks);
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
        return new UnmodifiableObservableList<>(sortedTasks);
    }
}
