package seedu.savvytasker.model;

import javafx.collections.ObservableList;
import seedu.savvytasker.model.person.ReadOnlyTask;
import seedu.savvytasker.model.person.Task;
import seedu.savvytasker.model.person.TaskList;
import seedu.savvytasker.model.person.TaskList.DuplicateTaskException;
import seedu.savvytasker.model.person.TaskList.TaskNotFoundException;

import java.util.*;
import java.util.stream.Collectors;

/**
 * Wraps all data at the savvy-tasker level
 * Duplicates are not allowed (by .equals comparison)
 */
public class SavvyTasker implements ReadOnlySavvyTasker {

    private final TaskList tasks;

    {
        tasks = new TaskList();
    }

    public SavvyTasker() {}

    /**
     * Persons and Tags are copied into this savvytasker
     */
    public SavvyTasker(ReadOnlySavvyTasker toBeCopied) {
        this(toBeCopied.getTaskList());
    }

    /**
     * Persons and Tags are copied into this savvytasker
     */
    public SavvyTasker(TaskList tasks) {
        resetData(tasks.getInternalList());
    }

    public static ReadOnlySavvyTasker getEmptySavvyTasker() {
        return new SavvyTasker();
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

    public void resetData(ReadOnlySavvyTasker newData) {
        resetData(newData.getReadOnlyListOfTasks());
    }

//// task-level operations
    
    /**
     * Returns the next available id for use to uniquely identify a task.
     * @author A0139915W
     * @return The next available id.
     */
    public int getNextTaskId() {
        return tasks.getNextId();
    }

    /**
     * Adds a task to savvy tasker.
     * @throws TaskList.DuplicateTaskException if an equivalent task already exists.
     */
    public void addTask(Task t) throws DuplicateTaskException {
        tasks.add(t);
    }
    
    /**
     * Removes a task from savvy tasker.
     * @param key the task to be removed
     * @return true if the task is removed successfully
     * @throws TaskNotFoundException if the task to be removed does not exist
     */
    public boolean removeTask(ReadOnlyTask key) throws TaskNotFoundException {
        if (tasks.remove(key)) {
            return true;
        } else {
            throw new TaskList.TaskNotFoundException();
        }
    }
    
    /**
     * Replaces a task from savvy tasker.
     * @param key the task to be replaced
     * @return true if the task is removed successfully
     * @throws TaskNotFoundException if the task to be removed does not exist
     */
    public boolean replaceTask(ReadOnlyTask key, Task replacement) throws TaskNotFoundException {
        if (tasks.contains(key)) {
            return tasks.replace(key, replacement);
        } else {
            throw new TaskList.TaskNotFoundException();
        }
    }

//// util methods

    @Override
    public String toString() {
        return tasks.getInternalList().size() + " tasks";
        // TODO: refine later
    }

    @Override
    public List<ReadOnlyTask> getReadOnlyListOfTasks() {
        return Collections.unmodifiableList(tasks.getInternalList());
    }

    @Override
    public TaskList getTaskList() {
        return tasks;
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof SavvyTasker // instanceof handles nulls
                && this.tasks.equals(((SavvyTasker) other).tasks));
    }

    @Override
    public int hashCode() {
        // use this method for custom fields hashing instead of implementing your own
        return Objects.hash(tasks);
    }
}
