package seedu.oneline.model.task;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import seedu.oneline.commons.exceptions.DuplicateDataException;
import seedu.oneline.commons.util.CollectionUtil;

import java.util.*;

import com.sun.javafx.logging.Logger;

/**
 * A list of persons that enforces uniqueness between its elements and does not allow nulls.
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
            super("Operation would result in duplicate persons");
        }
    }

    /**
     * Signals that an operation targeting a specified person in the list would fail because
     * there is no such matching person in the list.
     */
    public static class TaskNotFoundException extends Exception {}

    private final ObservableList<Task> internalList = FXCollections.observableArrayList();

    /**
     * Constructs empty PersonList.
     */
    public UniqueTaskList() {}

    /**
     * Returns true if the list contains an equivalent person as the given argument.
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
     * Replaces the old task with the new task at the same index in the list
     *
     * @throws TaskNotFoundException if no such task could be found in the list.
     */
    public void replaceTask(ReadOnlyTask oldTask, Task newTask) throws TaskNotFoundException, DuplicateTaskException {
        assert oldTask != null;
        assert newTask != null;
        int index = internalList.indexOf(oldTask);
        if (index == -1) {
            throw new TaskNotFoundException();
        }
        if (internalList.contains(newTask)) {
            throw new DuplicateTaskException();
        }
        internalList.remove(index);
        internalList.add(index, newTask);
    }
    
    /**
     * Removes the equivalent person from the list.
     *
     * @throws TaskNotFoundException if no such person could be found in the list.
     */
    public boolean remove(ReadOnlyTask toRemove) throws TaskNotFoundException {
        assert toRemove != null;
        final boolean personFoundAndDeleted = internalList.remove(toRemove);
        if (!personFoundAndDeleted) {
            throw new TaskNotFoundException();
        }
        return personFoundAndDeleted;
    }
    
    /**
     * Marks a task as done
     * @return
     */
    public Task done(int index) throws TaskNotFoundException {
        Task toDone = internalList.get(index);
        assert toDone != null;
        toDone.setCompleted(true);
//        internalList.set(index - 1, toDone);
        internalList.remove(toDone);
        return toDone;
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
