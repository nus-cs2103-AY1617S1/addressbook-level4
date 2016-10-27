package seedu.task.model.item;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import seedu.task.commons.exceptions.DuplicateDataException;
import seedu.task.commons.util.CollectionUtil;

import java.util.*;

/**
 * A list of tasks that enforces uniqueness between its elements and does not allow nulls.
 *
 * Supports a minimal set of list operations.
 *
 * @see Event#equals(Object)
 * @see CollectionUtil#elementsAreUnique(Collection)
 */
public class UniqueEventList implements Iterable<Event> {

    /**
     * Signals that an operation would have violated the 'no duplicates' property of the list.
     */
    public static class DuplicateEventException extends DuplicateDataException {
        protected DuplicateEventException() {
            super("Operation would result in duplicate tasks");
        }
    }

    /**
     * Signals that an operation targeting a specified task in the list would fail because
     * there is no such matching task in the list.
     */
    public static class EventNotFoundException extends Exception {}

    private final ObservableList<Event> internalList = FXCollections.observableArrayList();

    /**
     * Constructs empty EventList.
     */
    public UniqueEventList() {}

    /**
     * Returns true if the list contains an equivalent event as the given argument.
     */
    public boolean contains(ReadOnlyEvent toCheck) {
        assert toCheck != null;
        return internalList.contains(toCheck);
    }

    //@@author A0127570H
    /**
     * Adds a event to the begining of list.
     *
     * @throws DuplicateEventException if the event to add is a duplicate of an existing event in the list.
     */
    public void add(Event toAdd) throws DuplicateEventException {
    	assert toAdd != null;
        if (contains(toAdd)) {
            throw new DuplicateEventException();
        }
        internalList.add(toAdd);
    }
    //@@author

    /**
     * Removes the equivalent event from the list.
     *
     * @throws EventNotFoundException if no such event could be found in the list.
     */
    public boolean remove(ReadOnlyEvent toRemove) throws EventNotFoundException {
        assert toRemove != null;
        final boolean taskFoundAndDeleted = internalList.remove(toRemove);
        if (!taskFoundAndDeleted) {
            throw new EventNotFoundException();
        }
        return taskFoundAndDeleted;
    }
    
    //@@author A0127570H
    /**
     * Edits an event in the list.
     *
     * @throws DuplicateEventException if the edited event is a duplicate of an existing event in the list.
     */
    public void edit(Event toEdit, ReadOnlyEvent targetEvent) throws UniqueEventList.DuplicateEventException {
        assert toEdit != null && targetEvent != null;
        if (contains(toEdit)) {
            throw new DuplicateEventException();
        }
        int index = internalList.indexOf(targetEvent);
        internalList.set(index, toEdit); 
    }
    //@@author

    public ObservableList<Event> getInternalList() {
        return internalList;
    }

    @Override
    public Iterator<Event> iterator() {
        return internalList.iterator();
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof UniqueEventList // instanceof handles nulls
                && this.internalList.equals(
                ((UniqueEventList) other).internalList));
    }

    @Override
    public int hashCode() {
        return internalList.hashCode();
    }
}
