package seedu.jimi.model.task;

import java.util.Collection;
import java.util.Iterator;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import seedu.jimi.commons.exceptions.DuplicateDataException;
import seedu.jimi.commons.util.CollectionUtil;
import seedu.jimi.model.event.Event;

/**
 * A list of tasks that enforces uniqueness between its elements and does not allow nulls.
 *
 * Supports a minimal set of list operations.
 *
 * @see T#equals(Object)
 * @see CollectionUtil#elementsAreUnique(Collection)
 */
public class UniqueTaskList<T> implements Iterable<T> {

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

    private final ObservableList<T> internalList = FXCollections.observableArrayList();

    /**
     * Constructs empty TaskList.
     */
    public UniqueTaskList() {}

    /**
     * Returns true if the list contains an equivalent task as the given argument.
     */
    public boolean contains(T toCheck) {
        assert toCheck != null;
        return internalList.contains(toCheck);
    }

    /**
     * Adds a task to the list.
     *
     * @throws DuplicateTaskException if the task to add is a duplicate of an existing task in the list.
     */
    public void add(T toAdd) throws DuplicateTaskException {
        assert toAdd != null;
        if (contains(toAdd)) {
            throw new DuplicateTaskException();
        }
        internalList.add(toAdd);
    }

    /**
     * Removes the equivalent task from the list.
     *
     * @throws TaskNotFoundException if no such task could be found in the list.
     */
    public boolean remove(T toRemove) throws TaskNotFoundException {
        assert toRemove != null;
        final boolean taskFoundAndDeleted = internalList.remove(toRemove);
        if (!taskFoundAndDeleted) {
            throw new TaskNotFoundException();
        }
        return taskFoundAndDeleted;
    }
    
    /**
     * Sets the selected task to be complete/incomplete.
     * @throws TaskNotFoundException 
     */
    public void complete(ReadOnlyTask toComplete, boolean isComplete) throws TaskNotFoundException {
        assert toComplete != null;
        int targetIndex = internalList.indexOf(toComplete);
        
        if (targetIndex == -1) {
            throw new UniqueTaskList.TaskNotFoundException();
        }
        
        if (toComplete instanceof DeadlineTask) {
            ((DeadlineTask) internalList.get(targetIndex)).setCompleted(isComplete);
        } else if (toComplete instanceof Event) {
            ((Event) internalList.get(targetIndex)).setCompleted(isComplete);
        } else {
            ((FloatingTask) internalList.get(targetIndex)).setCompleted(isComplete);
        }
    }
    
    /**
     * Replaces the floating task at the specified index with {@code toEdit}
     */
    public void replace(T oldTask, T newTask) {
        assert newTask != null;
        int oldIdx = internalList.indexOf(oldTask);
        internalList.set(oldIdx, newTask);
    }

    public ObservableList<T> getInternalList() {
        return internalList;
    }

    @Override
    public Iterator<T> iterator() {
        Iterator<T> i = internalList.iterator();
        return i;
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