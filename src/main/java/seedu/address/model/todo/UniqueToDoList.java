package seedu.address.model.todo;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import seedu.address.commons.util.CollectionUtil;
import seedu.address.commons.exceptions.DuplicateDataException;

import java.util.*;

/**
 * A list of ToDos that enforces uniqueness between its elements and does not allow nulls.
 *
 * Supports a minimal set of list operations.
 *
 * @see ToDo#equals(Object)
 * @see CollectionUtil#elementsAreUnique(Collection)
 */
public class UniqueToDoList implements Iterable<ToDo> {

    /**
     * Signals that an operation would have violated the 'no duplicates' property of the list.
     */
    public static class DuplicateToDoException extends DuplicateDataException {
        protected DuplicateToDoException() {
            super("Operation would result in duplicate ToDos");
        }
    }

    /**
     * Signals that an operation targeting a specified ToDo in the list would fail because
     * there is no such matching ToDo in the list.
     */
    public static class ToDoNotFoundException extends Exception {}

    private final ObservableList<ToDo> internalList = FXCollections.observableArrayList();

    /**
     * Constructs empty ToDoList.
     */
    public UniqueToDoList() {}

    /**
     * Returns true if the list contains an equivalent ToDo as the given argument.
     */
    public boolean contains(ReadOnlyToDo toCheck) {
        assert toCheck != null;
        return internalList.contains(toCheck);
    }

    /**
     * Adds a ToDo to the list.
     *
     * @throws DuplicateToDoException if the ToDo to add is a duplicate of an existing ToDo in the list.
     */
    public void add(ToDo toAdd) throws DuplicateToDoException {
        assert toAdd != null;
        if (contains(toAdd)) {
            throw new DuplicateToDoException();
        }
        internalList.add(toAdd);
    }

    /**
     * Removes the equivalent ToDo from the list.
     *
     * @throws ToDoNotFoundException if no such ToDo could be found in the list.
     */
    public boolean remove(ReadOnlyToDo toRemove) throws ToDoNotFoundException {
        assert toRemove != null;
        final boolean ToDoFoundAndDeleted = internalList.remove(toRemove);
        if (!ToDoFoundAndDeleted) {
            throw new ToDoNotFoundException();
        }
        return ToDoFoundAndDeleted;
    }

    public ObservableList<ToDo> getInternalList() {
        return internalList;
    }

    @Override
    public Iterator<ToDo> iterator() {
        return internalList.iterator();
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof UniqueToDoList // instanceof handles nulls
                && this.internalList.equals(
                ((UniqueToDoList) other).internalList));
    }

    @Override
    public int hashCode() {
        return internalList.hashCode();
    }
}
