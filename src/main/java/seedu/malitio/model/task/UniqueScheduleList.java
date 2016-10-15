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
public class UniqueScheduleList implements Iterable<Schedule> {

    /**
     * Signals that an operation would have violated the 'no duplicates' property of the list.
     */
    public static class DuplicateScheduleException extends DuplicateDataException {
        protected DuplicateScheduleException() {
            super("Operation would result in duplicate event/deadline");
        }
    }

    /**
     * Signals that an operation targeting a specified task in the list would fail because
     * there is no such matching task in the list.
     */
    public static class ScheduleNotFoundException extends Exception {}

    private final ObservableList<Schedule> internalList = FXCollections.observableArrayList();

    /**
     * Constructs empty TaskList.
     */
    public UniqueScheduleList() {}

    /**
     * Returns true if the list contains an equivalent task as the given argument.
     */
    public boolean contains(ReadOnlySchedule toCheck) {
        assert toCheck != null;
        return internalList.contains(toCheck);
    }

    /**
     * Adds a task to the list.
     *
     * @throws DuplicateTaskException if the task to add is a duplicate of an existing task in the list.
     */
    public void add(Schedule toAdd) throws DuplicateScheduleException {
        assert toAdd != null;
        if (contains(toAdd)) {
            throw new DuplicateScheduleException();
        }
        internalList.add(toAdd);
    }

    /**
     * Removes the equivalent schedule from the list.
     *
     * @throws TaskNotFoundException if no such task could be found in the list.
     */
    public boolean remove(ReadOnlySchedule toRemove) throws ScheduleNotFoundException {
        assert toRemove != null;
        final boolean scheduleFoundAndDeleted = internalList.remove(toRemove);
        if (!scheduleFoundAndDeleted) {
            throw new ScheduleNotFoundException();
        }
        return scheduleFoundAndDeleted;
    }

    public ObservableList<Schedule> getInternalList() {
        return internalList;
    }

    @Override
    public Iterator<Schedule> iterator() {
        return internalList.iterator();
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof UniqueScheduleList // instanceof handles nulls
                && this.internalList.equals(
                ((UniqueScheduleList) other).internalList));
    }

    @Override
    public int hashCode() {
        return internalList.hashCode();
    }
}

