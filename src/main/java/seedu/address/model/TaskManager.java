package seedu.address.model;

import javafx.collections.ObservableList;
import seedu.address.model.task.Task;
import seedu.address.model.task.Time;
import seedu.address.model.task.Name;
import seedu.address.model.task.ReadOnlyTask;
import seedu.address.model.task.Recurrence;
import seedu.address.model.task.UniqueTaskList;
import seedu.address.model.task.UniqueTaskList.TaskNotFoundException;
import seedu.address.commons.exceptions.IllegalValueException;

import java.util.*;
import java.util.stream.Collectors;

/**
 * Wraps all data at the task-manager level
 * Duplicates are not allowed (by .equals comparison)
 */
public class TaskManager implements ReadOnlyTaskManager {

    private final UniqueTaskList tasks;

    {
        tasks = new UniqueTaskList();
    }

    public TaskManager() {}

    /**
     * Tasks are copied into this task manager
     */
    public TaskManager(ReadOnlyTaskManager toBeCopied) {
        this(toBeCopied.getUniqueTaskList());
    }

    /**
     * Tasks are copied into this task manager
     */
    public TaskManager(UniqueTaskList tasks) {
        resetData(tasks.getInternalList());
    }

    public static ReadOnlyTaskManager getEmptyTaskManager() {
        return new TaskManager();
    }

//// list overwrite operations

    public ObservableList<Task> getTasks() {
        return tasks.getInternalList();
    }

    public void setTasks(List<Task> tasks) {
        this.tasks.getInternalList().setAll(tasks);
    }

    public void resetData(Collection<? extends ReadOnlyTask> newTasks) {
        setTasks(newTasks.stream().map(Task::new).collect(Collectors.toList()));
    }

    public void resetData(ReadOnlyTaskManager newData) {
        resetData(newData.getTaskList());
    }

//// task-level operations

    /**
     * Adds a task to the task manager.
     *
     * @throws UniqueTaskList.DuplicateTaskException if an equivalent task already exists.
     */
    public void addTask(Task p) throws UniqueTaskList.DuplicateTaskException {
        tasks.add(p);
    }

    public boolean removeTask(ReadOnlyTask key) throws UniqueTaskList.TaskNotFoundException {
        if (tasks.remove(key)) {
            return true;
        } else {
            throw new UniqueTaskList.TaskNotFoundException();
        }
    }
    
    public void doneTask(ReadOnlyTask task) throws UniqueTaskList.TaskNotFoundException {
    	tasks.done(task);
    }
    
    public void undoneTask(ReadOnlyTask task) throws UniqueTaskList.TaskNotFoundException {
    	tasks.undone(task);
    }

    public void editTaskName(ReadOnlyTask task, String newInfo) throws UniqueTaskList.TaskNotFoundException, IllegalValueException {
        tasks.editTaskName(task, new Name(newInfo));
    }
    
    public void editTaskStartTime(ReadOnlyTask task, String newInfo) throws UniqueTaskList.TaskNotFoundException, IllegalValueException {
        tasks.editStartTime(task, new Time(newInfo));
    }
    
    public void editTaskEndTime(ReadOnlyTask task, String newInfo) throws UniqueTaskList.TaskNotFoundException, IllegalValueException {
        tasks.editEndTime(task, new Time(newInfo));
    }
    
    public void editTaskRecurFreq(ReadOnlyTask task, String newRecur) throws TaskNotFoundException {
        tasks.editRecurFreq(task, new Recurrence(newRecur));
    }
    
//// util methods

    @Override
    public String toString() {
        return tasks.getInternalList().size() + " tasks, ";
    }

    @Override
    public List<ReadOnlyTask> getTaskList() {
        return Collections.unmodifiableList(tasks.getInternalList());
    }

    @Override
    public UniqueTaskList getUniqueTaskList() {
        return this.tasks;
    }


    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof TaskManager // instanceof handles nulls
                && this.tasks.equals(((TaskManager) other).tasks));
    }

    @Override
    public int hashCode() {
        // use this method for custom fields hashing instead of implementing your own
        return Objects.hash(tasks);
    }

}
