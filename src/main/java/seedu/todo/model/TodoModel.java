package seedu.todo.model;

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

public class TodoModel implements Model {
    private TodoListModel todolist;
    
    public TodoModel(Config config) {
        this(new TodoListStorage(config.getTodoListFilePath()));
    }
    
    public TodoModel(MoveableStorage<ImmutableTodoList> storage) {
        this.todolist = new TodoList(storage);
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
        return todolist.delete(index);
    }

    @Override
    public ImmutableTask update(int index, Consumer<MutableTask> update) throws ValidationException {
        return todolist.update(index, update);
    }

    @Override
    public void view(Predicate<ImmutableTask> filter, Comparator<ImmutableTask> sort) {
        todolist.view(filter, sort);
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
        return todolist.getStorageLocation();
    }

    @Override
    public UnmodifiableObservableList<ImmutableTask> getObserveableList() {
        return todolist.getObserveableList();
    }
}
