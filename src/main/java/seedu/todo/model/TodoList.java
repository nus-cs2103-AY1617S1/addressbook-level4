package seedu.todo.model;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.time.LocalDateTime;
import java.util.Collections;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;
import java.util.function.Consumer;
import java.util.logging.Logger;
import java.util.stream.Collectors;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
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
import seedu.todo.storage.MovableStorage;

//@@author A0135817B
/**
 * Represents the todolist inside memory. While Model works as the external 
 * interface for handling data and application state, this class is internal 
 * to Model and represents only CRUD operations to the todolist. 
 */
public class TodoList implements TodoListModel {
    private static final String INCORRECT_FILE_FORMAT_FORMAT = "%s doesn't seem to be in the correct format.";
    private static final String FILE_NOT_FOUND_FORMAT = "%s does not seem to exist.";
    private static final String FILE_SAVE_ERROR_FORMAT = "Couldn't save file: %s";

    private ObservableList<Task> tasks = FXCollections.observableArrayList(Task::getObservableProperties);

    private MovableStorage<ImmutableTodoList> storage;

    private static final Logger logger = LogsCenter.getLogger(TodoList.class);
    private static final EventsCenter events = EventsCenter.getInstance();

    public TodoList(MovableStorage<ImmutableTodoList> storage) {
        this.storage = storage;
        
        try {
            setTasks(storage.read().getTasks(), false);
        } catch (FileNotFoundException | DataConversionException e) {
            logger.info("Data file not found. Will be starting with an empty TodoList");
        }
        
        // Update event status 
        new Timer().scheduleAtFixedRate(new UpdateEventTask(), 0, 60 * 1000);
    }
    
    private void updateEventStatus() {
        LocalDateTime now = LocalDateTime.now();
        boolean todoListModified = false;
        
        for (Task task : tasks) {
            boolean isIncompleteEvent = !task.isCompleted() && task.isEvent();
            if (isIncompleteEvent && now.isAfter(task.getEndTime().get())) {
                task.setCompleted(true);
                todoListModified = true;
            }
        }
        if (todoListModified) {
            saveTodoList();
        }
    }

    private void raiseStorageEvent(String message, Exception e) {
        // TODO: Have this raise an event
    }
    
    private void saveTodoList() {
        try {
            storage.save(this);
        } catch (IOException e) {
            events.post(new DataSavingExceptionEvent(e));
        }
    }

    @Override
    public ImmutableTask add(String title) {
        Task task = new Task(title);
        tasks.add(task);
        
        saveTodoList();
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
        Task task = tasks.remove(index);
        saveTodoList();
        return task;
    }

    @Override
    public ImmutableTask update(int index, Consumer<MutableTask> update) throws ValidationException {
        Task task = tasks.get(index);
        ValidationTask validationTask = new ValidationTask(task);
        update.accept(validationTask);
        validationTask.validate();

        // changes are validated and accepted
        update.accept(task);
        saveTodoList();
        return task;
    }

    //@@author A0092382A
    @Override
    public void updateAll(List<Integer> indexes, Consumer<MutableTask> update) throws ValidationException {
        for (Integer x: indexes) {
            MutableTask task = tasks.get(x);
            ValidationTask validationTask = new ValidationTask(task);
            update.accept(validationTask);
            validationTask.validate();
        }
        
        for (Integer i : indexes) {
            MutableTask task = tasks.get(i);
            update.accept(task);
        }
        
        saveTodoList();
        
    } 
    

    @Override
    public void save(String location) throws ValidationException {
        try {
            storage.save(this, location);
        } catch (IOException e) {
            String message = String.format(TodoList.FILE_SAVE_ERROR_FORMAT, e.getMessage());
            throw new ValidationException(message);
        }
    }

    @Override
    public void load(String location) throws ValidationException {
        try {
            setTasks(storage.read(location).getTasks());
        } catch (DataConversionException e) {
            throw new ValidationException(TodoList.INCORRECT_FILE_FORMAT_FORMAT);
        } catch (FileNotFoundException e) {
            String message = String.format(TodoList.FILE_NOT_FOUND_FORMAT, location);
            throw new ValidationException(message);
        }
    }

    @Override
    public void setTasks(List<ImmutableTask> todoList) {
        setTasks(todoList, true);
    }

    /**
     * We have a private version of setTasks because we also need to setTask during initialization, 
     * but we don't want the list to be save during init (where we presumably got the data from)
     */
    private void setTasks(List<ImmutableTask> todoList, boolean persistToStorage) {
        this.tasks.clear();
        this.tasks.addAll(todoList.stream().map(Task::new).collect(Collectors.toList()));
        
        if (persistToStorage) {
            saveTodoList();
        }
    }

    @Override
    public ObservableList<ImmutableTask> getObservableList() {
        return new UnmodifiableObservableList<>(tasks);
    }

    @Override
    public List<ImmutableTask> getTasks() {
        return Collections.unmodifiableList(tasks);
    }
    
    private class UpdateEventTask extends TimerTask {
        @Override
        public void run() {
            updateEventStatus();
        }
    }

}
