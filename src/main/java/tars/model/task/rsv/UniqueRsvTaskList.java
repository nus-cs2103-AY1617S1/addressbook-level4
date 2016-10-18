package tars.model.task.rsv;

import java.util.Collection;
import java.util.Iterator;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import tars.commons.exceptions.DuplicateTaskException;
import tars.commons.util.CollectionUtil;

/**
 * A list of reserved tasks that enforces uniqueness between its elements and
 * does not allow nulls.
 *
 * Supports a minimal set of list operations.
 * 
 * @@author A0124333U
 *
 * @see RsvTask#equals(Object)
 * @see CollectionUtil#elementsAreUnique(Collection)
 */

public class UniqueRsvTaskList implements Iterable<RsvTask> {

    /**
     * Signals that an operation targeting a specified task in the list would
     * fail because there is no such matching task in the list.
     */
    public static class TaskNotFoundException extends Exception {
    }

    private final ObservableList<RsvTask> internalList = FXCollections.observableArrayList();

    /**
     * Constructs empty RsvTaskList.
     */
    public UniqueRsvTaskList() {
    }

    /**
     * Returns true if the list contains an equivalent reserved task as the
     * given argument.
     */
    public boolean contains(RsvTask toCheck) {
        assert toCheck != null;
        return internalList.contains(toCheck);
    }

    /**
     * Adds a reserved task to the list.
     *
     * @throws DuplicateTaskException
     *             if the reserved task to add is a duplicate of an existing
     *             reserved task in the list.
     */
    public void add(RsvTask toAdd) throws DuplicateTaskException {
        assert toAdd != null;
        if (contains(toAdd)) {
            throw new DuplicateTaskException();
        }
        internalList.add(toAdd);
    }

    /**
     * Removes the equivalent task from the list.
     *
     * @throws TaskNotFoundException
     *             if no such task could be found in the list.
     */
    public boolean remove(RsvTask toRemove) throws TaskNotFoundException {
        assert toRemove != null;
        final boolean taskFoundAndDeleted = internalList.remove(toRemove);
        if (!taskFoundAndDeleted) {
            throw new TaskNotFoundException();
        }
        return taskFoundAndDeleted;
    }

    public ObservableList<RsvTask> getInternalList() {
        return internalList;
    }

    @Override
    public Iterator<RsvTask> iterator() {
        return internalList.iterator();
    }
    
    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof UniqueRsvTaskList // instanceof handles nulls
                && this.internalList.equals(
                ((UniqueRsvTaskList) other).internalList));
    }

    @Override
    public int hashCode() {
        return internalList.hashCode();
    }

}
