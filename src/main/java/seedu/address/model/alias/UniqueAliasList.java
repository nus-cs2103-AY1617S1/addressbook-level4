package seedu.address.model.alias;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import seedu.address.commons.util.CollectionUtil;
import seedu.address.commons.exceptions.DuplicateDataException;

import java.util.*;

/**
 * A list of aliases that enforces uniqueness between its elements and does not allow nulls.
 *
 * Supports a minimal set of list operations.
 *
 * @see Alias#equals(Object)
 * @see CollectionUtil#elementsAreUnique(Collection)
 */
public class UniqueAliasList implements Iterable<Alias> {

    /**
     * Signals that an operation would have violated the 'no duplicates' property of the list.
     */
    public static class DuplicateAliasException extends DuplicateDataException {
        protected DuplicateAliasException() {
            super("Operation would result in duplicate aliases.");
        }
    }

    /**
     * Signals that an operation targeting a specified alias in the list would fail because
     * there is no such matching task in the list.
     */
    public static class AliasNotFoundException extends Exception {}

    private final ObservableList<Alias> internalList = FXCollections.observableArrayList();


    /**
     * Constructs empty AliasList.
     */
    public UniqueAliasList() {}

    /**
     * Returns true if the list contains an equivalent alias as the given argument.
     */
    public boolean contains(ReadOnlyAlias toCheck) {
        assert toCheck != null;
        return internalList.contains(toCheck);
    }

    /**
     * Adds a task to the list.
     *
     * @throws DuplicateAliasException if the alias to add is a duplicate of an existing task in the list.
     */
    public void add(Alias toAdd) throws DuplicateAliasException {
        assert toAdd != null;
        if (contains(toAdd)) {
            throw new DuplicateAliasException();
        }
        internalList.add(toAdd);
    }

    /**
     * Removes the equivalent task from the list.
     *
     * @throws TaskNotFoundException if no such task could be found in the list.
     */
    public boolean remove(ReadOnlyAlias toRemove) throws AliasNotFoundException {
        assert toRemove != null;
        final boolean aliasFoundAndDeleted = internalList.remove(toRemove);
        if (!aliasFoundAndDeleted) {
            throw new AliasNotFoundException();
        }
        return aliasFoundAndDeleted;
    }
    
    /**
     * set the equivalent task to the specified index of the list
     * @throws TaskNotFoundException if no such task could be found in the list.
     */ 			
    public boolean set(int key, Alias toSet) throws AliasNotFoundException {
        assert toSet != null;
        boolean isFound = false;
        if (internalList.size() < key) {
            throw new AliasNotFoundException();
        } else {
        	internalList.set(key, toSet);
        	isFound = true;
        }
        return isFound;
    }

    public ObservableList<Alias> getInternalList() {
        return internalList;
    }

    @Override
    public Iterator<Alias> iterator() {
        return internalList.iterator();
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof UniqueAliasList // instanceof handles nulls
                && this.internalList.equals(
                ((UniqueAliasList) other).internalList));
    }

    @Override
    public int hashCode() {
        return internalList.hashCode();
    }

}
