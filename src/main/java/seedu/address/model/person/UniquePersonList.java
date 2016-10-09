package seedu.address.model.person;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import seedu.address.commons.util.CollectionUtil;
import seedu.address.commons.exceptions.DuplicateDataException;

import java.util.*;

/**
 * A list of persons that enforces uniqueness between its elements and does not allow nulls.
 *
 * Supports a minimal set of list operations.
 *
 * @see FloatingTask#equals(Object)
 * @see CollectionUtil#elementsAreUnique(Collection)
 */
public class UniquePersonList implements Iterable<Entry> {

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
    public static class PersonNotFoundException extends Exception {}

    private final ObservableList<Entry> internalList = FXCollections.observableArrayList();

    /**
     * Constructs empty PersonList.
     */
    public UniquePersonList() {}

    /**
     * Returns true if the list contains an equivalent person as the given argument.
     */
    public boolean contains(Entry toCheck) {
        assert toCheck != null;
        return internalList.contains(toCheck);
    }

    /**
     * Adds a person to the list.
     *
     * @throws DuplicateTaskException
     *             if the task to add is a duplicate of an existing task in the
     *             list.
     */
    public void add(Entry person) throws DuplicateTaskException {
        assert person != null;
        if (contains(person)) {
            throw new DuplicateTaskException();
        }
        internalList.add(person);
    }

    /**
     * Edit an entry on the list.
     * 
     * @param toEdit
     *            the entry to be edited
     * @param newTitle
     *            the new title for the entry
     * @throws PersonNotFoundException
     *             if no such person could be found in the list.
     * @throws DuplicateTaskException
     *             if the task to add is a duplicate of an existing task.
     */
    public void edit(Entry toEdit, Title newTitle) throws PersonNotFoundException, DuplicateTaskException {
        assert toEdit != null;
        if (contains(toEdit)) {
            throw new DuplicateTaskException();
        }
        // TODO: This should be atomic or we should implement notifications for
        // mutations
        remove(toEdit);
        toEdit.setTitle(newTitle);
        add(toEdit);
    }

    /**
     * Removes the equivalent person from the list.
     *
     * @throws PersonNotFoundException if no such person could be found in the list.
     */
    public boolean remove(Entry toRemove) throws PersonNotFoundException {
        assert toRemove != null;
        final boolean personFoundAndDeleted = internalList.remove(toRemove);
        if (!personFoundAndDeleted) {
            throw new PersonNotFoundException();
        }
        return personFoundAndDeleted;
    }

    public ObservableList<Entry> getInternalList() {
        return internalList;
    }

    @Override
    public Iterator<Entry> iterator() {
        return internalList.iterator();
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof UniquePersonList // instanceof handles nulls
                && this.internalList.equals(
                ((UniquePersonList) other).internalList));
    }

    @Override
    public int hashCode() {
        return internalList.hashCode();
    }
}
