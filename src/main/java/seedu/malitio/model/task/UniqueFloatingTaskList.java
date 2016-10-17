package seedu.malitio.model.task;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import seedu.malitio.commons.exceptions.DuplicateDataException;
import seedu.malitio.commons.util.CollectionUtil;

import java.util.*;

/**
 * A list of tasks that enforces uniqueness between its elements and does not allow nulls.
 *
 * Supports a minimal set of list operations.
 *
 * @see Task#equals(Object)
 * @see CollectionUtil#elementsAreUnique(Collection)
 */
public class UniqueFloatingTaskList implements Iterable<FloatingTask> {

    /**
     * Signals that an operation would have violated the 'no duplicates' property of the list.
     */
    public static class DuplicateFloatingTaskException extends DuplicateDataException {
        protected DuplicateFloatingTaskException() {
            super("Operation would result in duplicate floating tasks");
        }
    }

    /**
     * Signals that an operation targeting a specified task in the list would fail because
     * there is no such matching task in the list.
     */
    public static class FloatingTaskNotFoundException extends Exception {}

    private final ObservableList<FloatingTask> internalList = FXCollections.observableArrayList();

    /**
     * Constructs empty TaskList.
     */
    public UniqueFloatingTaskList() {}

    /**
     * Returns true if the list contains an equivalent task as the given argument.
     */
    public boolean contains(ReadOnlyFloatingTask toCheck) {
        assert toCheck != null;
        return internalList.contains(toCheck);
    }

    /**
     * Adds a task to the list.
     *
     * @throws DuplicateFloatingTaskException if the task to add is a duplicate of an existing task in the list.
     */
    public void add(FloatingTask toAdd) throws DuplicateFloatingTaskException {
        assert toAdd != null;
        if (contains(toAdd)) {
            throw new DuplicateFloatingTaskException();
        }
        internalList.add(toAdd);
    }

    /**
     * Removes the equivalent task from the list.
     *
     * @throws FloatingTaskNotFoundException if no such task could be found in the list.
     */
    public boolean remove(ReadOnlyFloatingTask toRemove) throws FloatingTaskNotFoundException {
        assert toRemove != null;
        final boolean taskFoundAndDeleted = internalList.remove(toRemove);
        if (!taskFoundAndDeleted) {
            throw new FloatingTaskNotFoundException();
        }
        return taskFoundAndDeleted;
    }

    public ObservableList<FloatingTask> getInternalList() {
        return internalList;
    }

    @Override
    public Iterator<FloatingTask> iterator() {
        return internalList.iterator();
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof UniqueFloatingTaskList // instanceof handles nulls
                && this.internalList.equals(
                ((UniqueFloatingTaskList) other).internalList));
    }

    @Override
    public int hashCode() {
        return internalList.hashCode();
    }
}
