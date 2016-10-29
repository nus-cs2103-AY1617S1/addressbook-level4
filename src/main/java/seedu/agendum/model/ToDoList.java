package seedu.agendum.model;

import javafx.collections.ObservableList;
import seedu.agendum.model.task.ReadOnlyTask;
import seedu.agendum.model.task.Task;
import seedu.agendum.model.task.UniqueTaskList;

import java.util.*;
import java.util.stream.Collectors;

/**
 * Wraps all data at the to do list level
 * Duplicates are not allowed (by .equals comparison)
 */
public class ToDoList implements ReadOnlyToDoList {

    private final UniqueTaskList tasks;

    {
        tasks = new UniqueTaskList();
    }

    public ToDoList() {}

    /**
     * Tasks are copied into this to do list
     */
    public ToDoList(ReadOnlyToDoList toBeCopied) {
        this(toBeCopied.getUniqueTaskList());
    }

    /**
     * Tasks are copied into this to do list
     */
    public ToDoList(UniqueTaskList tasks) {
        resetData(tasks.getInternalList());
    }

    public static ReadOnlyToDoList getEmptyToDoList() {
        return new ToDoList();
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

    public void resetData(ReadOnlyToDoList newData) {
        resetData(newData.getTaskList());
    }

//// task-level operations

    /**
     * Adds a task to the to-do list.
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

    //@@author A0133367E
    /**
     * Updates an existing task in the to-do list.
     * @throws UniqueTaskList.DuplicateTaskException if an equivalent task (to updatedTask) already exists.
     * @throws UniqueTaskList.TaskNotFoundException if no such task (key) could be found in the list.
     */
    public boolean updateTask(ReadOnlyTask key, Task updatedTask)
            throws UniqueTaskList.TaskNotFoundException,
            UniqueTaskList.DuplicateTaskException {
        return tasks.update(key, updatedTask);
    }

    public boolean markTask(ReadOnlyTask key) throws UniqueTaskList.TaskNotFoundException {
        if (tasks.mark(key)) {
            return true;
        } else {
            throw new UniqueTaskList.TaskNotFoundException();
        }
    }

    public boolean unmarkTask(ReadOnlyTask key) throws UniqueTaskList.TaskNotFoundException {
        if (tasks.unmark(key)) {
            return true;
        } else {
            throw new UniqueTaskList.TaskNotFoundException();
        }
    }
    //@@author

//// util methods

    @Override
    public String toString() {
        return tasks.getInternalList().size() + " tasks, " ;
        // TODO: refine later
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
                || (other instanceof ToDoList // instanceof handles nulls
                && this.tasks.equals(((ToDoList) other).tasks));
    }

    @Override
    public int hashCode() {
        // use this method for custom fields hashing instead of implementing your own
        return Objects.hash(tasks);
    }
}
