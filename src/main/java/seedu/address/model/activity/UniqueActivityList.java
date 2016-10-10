package seedu.address.model.activity;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import seedu.address.commons.util.CollectionUtil;
import seedu.address.model.activity.Activity;
import seedu.address.model.activity.ReadOnlyActivity;
import seedu.address.commons.exceptions.DuplicateDataException;

import java.util.*;

/**
 * A list of persons that enforces uniqueness between its elements and does not allow nulls.
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
     * Signals that an operation targeting a specified person in the list would fail because
     * there is no such matching person in the list.
     */
    public static class ActivityNotFoundException extends Exception {}

    private final ObservableList<Activity> internalList = FXCollections.observableArrayList();

    /**
     * Constructs empty PersonList.
     */
    public UniqueActivityList() {}

    /**
     * Returns true if the list contains an equivalent person as the given argument.
     */
    public boolean contains(ReadOnlyActivity toCheck) {
        assert toCheck != null;
        return internalList.contains(toCheck);
    }

    /**
     * Adds a person to the list.
     *
     * @throws DuplicateActivityException if the person to add is a duplicate of an existing person in the list.
     */
    public void add(Activity toAdd) throws DuplicateActivityException {
        assert toAdd != null;
        if (contains(toAdd)) {
            throw new DuplicateActivityException();
        }
        internalList.add(toAdd);
    }

    /**
     * Removes the equivalent person from the list.
     *
     * @throws ActivityNotFoundException if no such person could be found in the list.
     */
    public boolean remove(ReadOnlyActivity toRemove) throws ActivityNotFoundException {
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
