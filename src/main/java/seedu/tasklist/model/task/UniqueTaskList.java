package seedu.tasklist.model.task;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import seedu.tasklist.commons.exceptions.DuplicateDataException;
import seedu.tasklist.commons.util.CollectionUtil;

import java.util.*;

/**
 * A list of persons that enforces uniqueness between its elements and does not allow nulls.
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
    public static class DuplicatePersonException extends DuplicateDataException {
        protected DuplicatePersonException() {
            super("Operation would result in duplicate persons");
        }
    }

    /**
     * Signals that an operation targeting a specified person in the list would fail because
     * there is no such matching person in the list.
     */
    public static class PersonNotFoundException extends Exception {}

    private final ObservableList<Task> internalList = FXCollections.observableArrayList();

    /**
     * Constructs empty PersonList.
     */
    public UniqueTaskList() {}

    /**
     * Returns true if the list contains an equivalent person as the given argument.
     */
    public boolean contains(ReadOnlyTask toCheck) {
        assert toCheck != null;
        return internalList.contains(toCheck);
    }

    /**
     * Adds a person to the list.
     *
     * @throws DuplicatePersonException if the person to add is a duplicate of an existing person in the list.
     */
    public void add(Task toAdd) throws DuplicatePersonException {
        assert toAdd != null;
        if (contains(toAdd)) {
            throw new DuplicatePersonException();
        }
        internalList.add(toAdd);
    }
    
    /**
     * Adds a person to the list.
     *
     * @throws PersonNotFoundException if no such person could be found in the list.
     */
    public boolean setComplete(ReadOnlyTask toComplete) throws PersonNotFoundException {
        assert toComplete != null;
        for (Task i: internalList){
        	if(i.getUniqueID()==toComplete.getUniqueID()){
        		i.markAsComplete();
        		return true;
        	}
        }
        throw new PersonNotFoundException();
    }
    

    /**
     * Removes the equivalent person from the list.
     *
     * @throws PersonNotFoundException if no such person could be found in the list.
     */
    public boolean remove(ReadOnlyTask toRemove) throws PersonNotFoundException {
        assert toRemove != null;
        for (Task i: internalList){
        	if(i.getUniqueID()==toRemove.getUniqueID()){
        		internalList.remove(i);
        		return true;
        	}
        }
        throw new PersonNotFoundException();
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
