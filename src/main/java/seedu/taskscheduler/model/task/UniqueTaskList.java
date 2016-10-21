package seedu.taskscheduler.model.task;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import seedu.taskscheduler.commons.exceptions.DuplicateDataException;
import seedu.taskscheduler.commons.util.CollectionUtil;
import seedu.taskscheduler.model.tag.UniqueTagList.DuplicateTagException;

import java.util.*;

/**
 * A list of tasks that enforces uniqueness between its elements and does not allow nulls.
 *
 * Supports a minimal set of list operations.
 *
 * @see Task#equals(Object)
 * @see CollectionUtil#elementsAreUnique(Collection)
 */
public class UniqueTaskList implements Iterable<Task> {

    /**
     * Signals that an operation would have violated the 'no duplicates' property of the list.
     */
    public static class DuplicateTaskException extends DuplicateDataException {
        protected DuplicateTaskException() {
            super("Operation would result in duplicate tasks");
        }
    }

    /**
     * Signals that an operation targeting a specified task in the list would fail because
     * there is no such matching task in the list.
     */
    public static class TaskNotFoundException extends Exception {}

    private final ObservableList<Task> internalList = FXCollections.observableArrayList();

    /**
     * Constructs empty TaskList.
     */
    public UniqueTaskList() {}

    /**
     * Returns true if the list contains an equivalent task as the given argument.
     */
    public boolean contains(ReadOnlyTask toCheck) {
        assert toCheck != null;
        return internalList.contains(toCheck);
    }

    /**
     * Adds a task to the list.
     *
     * @throws DuplicateTaskException if the task to add is a duplicate of an existing task in the list.
     */
    public void add(Task toAdd) throws DuplicateTaskException {
        assert toAdd != null;
        if (contains(toAdd)) {
            throw new DuplicateTaskException();
        }
        internalList.add(toAdd);
    }
    
    /**
     * Edits a task to the list.
     *
     * @throws DuplicateTaskException if the task to add is a duplicate of an existing task in the list.
     */
    public void edit(ReadOnlyTask toEdit, Task toCopy) throws DuplicateTaskException, TaskNotFoundException{
        assert toEdit != null;
        if (contains(toCopy)) {
            throw new DuplicateTaskException();
        }
        int index = internalList.indexOf(toEdit);
        if (index < 0) {
            throw new TaskNotFoundException();
        }
        internalList.set(index, toCopy);
    }
    
    /**
     * Marks a task to the list as completed.
     *
     * @throws TaskNotFoundException
     * @throws DuplicateTagException if the task is already complete.
     */
    public void mark(ReadOnlyTask toMark) throws TaskNotFoundException, DuplicateTagException{
        assert toMark != null;
        int index = internalList.indexOf(toMark);
        if (index < 0) {
            throw new TaskNotFoundException();
        }
        Task newTask = new Task(toMark);
        newTask.markComplete();
        internalList.set(index, newTask);
    }
    
    /**
     * Replace a task in the list with another task.
     *
     * @throws TaskNotFoundException
     */
    public void replace(Task oldTask, Task newTask) throws TaskNotFoundException {
        assert oldTask != null;
        int index = internalList.indexOf(oldTask);
        if (index < 0) {
            throw new TaskNotFoundException();
        }
        internalList.set(index, newTask);
    }
    
    /**
     * Insert a task into another task's position in the list.
     *
     * @throws TaskNotFoundException
     */
    public void insert(int index, Task newTask) throws TaskNotFoundException {
        assert newTask != null;
        assert index > 0;
        internalList.add(index-1, newTask);
    }

    /**
     * Removes the equivalent task from the list.
     *
     * @throws TaskNotFoundException if no such task could be found in the list.
     */
    public boolean remove(ReadOnlyTask toRemove) throws TaskNotFoundException {
        assert toRemove != null;
        final boolean taskFoundAndDeleted = internalList.remove(toRemove);
        if (!taskFoundAndDeleted) {
            throw new TaskNotFoundException();
        }
        return taskFoundAndDeleted;
    }

    public ObservableList<Task> getInternalList() {
        return internalList;
    }

    @Override
    public Iterator<Task> iterator() {
        return internalList.iterator();
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof UniqueTaskList // instanceof handles nulls
                && this.internalList.equals(
                ((UniqueTaskList) other).internalList));
    }

    @Override
    public int hashCode() {
        return internalList.hashCode();
    }
}
