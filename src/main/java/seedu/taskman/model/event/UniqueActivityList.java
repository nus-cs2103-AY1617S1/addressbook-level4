package seedu.taskman.model.event;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import seedu.taskman.commons.util.CollectionUtil;
import seedu.taskman.commons.exceptions.DuplicateDataException;

import java.util.*;

/**
 * A list of activities that enforces uniqueness between its elements and does not allow nulls.
 *
 * Supports a minimal set of list operations.
 *
 * @see Activity#equals(Object)
 * @see CollectionUtil#elementsAreUnique(Collection)
 */
public class UniqueActivityList implements Iterable<Activity> {

    /**
     * Signals that an operation would have violated the 'no duplicates' property of the list.
     */
    public static class DuplicateActivityException extends DuplicateDataException {
        protected DuplicateActivityException() {
            super("Operation would result in duplicate activities");
        }
    }

    /**
     * Signals that an operation targeting a specified task in the list would fail because
     * there is no such matching task in the list.
     */
    public static class ActivityNotFoundException extends Exception {}

    private final ObservableList<Activity> internalList = FXCollections.observableArrayList();

    /**
     * Constructs empty TaskList.
     */
    public UniqueActivityList() {}

    /**
     * Returns true if the list contains an equivalent task as the given argument.
     */
    public boolean contains(Activity toCheck) {
        assert toCheck != null;
        return internalList.contains(toCheck);
    }

    /**
     * Adds a task to the list.
     *
     * @throws DuplicateActivityException if the task to add is a duplicate of an existing task in the list.
     */
    public void add(Activity toAdd) throws DuplicateActivityException {
        assert toAdd != null;
        if (contains(toAdd)) {
            throw new DuplicateActivityException();
        }
        internalList.add(toAdd);
    }

    /**
     * Removes the equivalent task from the list.
     *
     * @throws ActivityNotFoundException if no such task could be found in the list.
     */
    public boolean remove(Activity toRemove) throws ActivityNotFoundException {
        assert toRemove != null;
        final boolean activityFoundAndDeleted = internalList.remove(toRemove);
        if (!activityFoundAndDeleted) {
            throw new ActivityNotFoundException();
        }
        return activityFoundAndDeleted;
    }

    public ObservableList<Activity> getInternalList() {
        return internalList;
    }

    @Override
    public Iterator<Activity> iterator() {
        return internalList.iterator();
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof UniqueActivityList // instanceof handles nulls
                && this.internalList.equals(
                ((UniqueActivityList) other).internalList));
    }

    @Override
    public int hashCode() {
        return internalList.hashCode();
    }
}
