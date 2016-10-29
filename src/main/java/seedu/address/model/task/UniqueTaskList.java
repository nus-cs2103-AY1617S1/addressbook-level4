package seedu.address.model.task;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import seedu.address.commons.util.CollectionUtil;
import seedu.address.commons.core.SortedObservableArrayList;
import seedu.address.commons.exceptions.DuplicateDataException;

import java.lang.reflect.Field;
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

    private final ObservableList<Task> internalList = new SortedObservableArrayList<Task>();
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
    
    //@@author A0135812L
    /**
     * Edits the equivalent task from the list.
     *
     * @throws TaskNotFoundException if no such task could be found in the list.
     * @throws SecurityException 
     * @throws NoSuchFieldException 
     * @throws IllegalAccessException 
     * @throws IllegalArgumentException 
     */
    public Task edit(ReadOnlyTask toEdit, HashMap<Field, Object> changes) throws TaskNotFoundException, IllegalArgumentException, IllegalAccessException, NoSuchFieldException, SecurityException {
        assert toEdit != null;
        final boolean taskFound = internalList.contains(toEdit);
        if (!taskFound) {
            throw new TaskNotFoundException();
        }else{
            final int taskFoundAt = internalList.indexOf(toEdit);
            Task original = internalList.get(taskFoundAt);
            Task edited = original.setFields(changes);
            internalList.set(taskFoundAt, edited);
            return edited;
        }
    }
    //@@author

    /**
     * Marks the equivalent task from the list.
     *
     * @throws TaskNotFoundException if no such task could be found in the list.
     */
    public void mark(ReadOnlyTask toMark) throws TaskNotFoundException {
        assert toMark != null;

        int idToMark = internalList.indexOf(toMark);
        // TODO: remove between the two todos when feedback has arrived.
        Task insertMark = internalList.get(idToMark);
        insertMark.toggleTaskStatus();
        remove(toMark);
        internalList.add(insertMark);
        // TODO: remove after getting feedback
        if (idToMark == -1)
            throw new TaskNotFoundException();
        // TODO FIX: this does not get updated internalList.get(idToMark).toggleTaskStatus();

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
