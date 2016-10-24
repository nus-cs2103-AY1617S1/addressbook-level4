package seedu.taskmanager.model.item;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import seedu.taskmanager.commons.exceptions.DuplicateDataException;
import seedu.taskmanager.commons.util.CollectionUtil;

import java.util.*;

/**
 * A list of items that enforces uniqueness between its elements and does not allow nulls.
 *
 * Supports a minimal set of list operations.
 *
 * @see Item#equals(Object)
 * @see CollectionUtil#elementsAreUnique(Collection)
 */
public class UniqueItemList implements Iterable<Item> {

    /**
     * Signals that an operation would have violated the 'no duplicates' property of the list.
     */
    public static class DuplicateItemException extends DuplicateDataException {
        protected DuplicateItemException() {
            super("Operation would result in duplicate items");
        }
    }

    /**
     * Signals that an operation targeting a specified item in the list would fail because
     * there is no such matching item in the list.
     */
    public static class ItemNotFoundException extends Exception {}

    private final ObservableList<Item> internalList = FXCollections.observableArrayList();

    /**
     * Constructs empty ItemList.
     */
    public UniqueItemList() {}

    /**
     * Returns true if the list contains an equivalent item as the given argument.
     */
    public boolean contains(ReadOnlyItem toCheck) {
        assert toCheck != null;
        return internalList.contains(toCheck);
    }

    /**
     * Adds an item to the list.
     *
     * @throws DuplicateItemException if the item to add is a duplicate of an existing item in the list.
     */
    public void add(Item toAdd) throws DuplicateItemException {
        assert toAdd != null;
        if (contains(toAdd)) {
            throw new DuplicateItemException();
        }
        internalList.add(toAdd);
    }

    //@@author A0140060A
    /**
     * Replaces an item in the list.
     *     
     * @throws ItemNotFoundException if no such item could be found in the list.
     * @throws DuplicateItemException if the replacing item is a duplicate of an existing item in the list.
     */
    public void replace(ReadOnlyItem target, Item toReplace) throws ItemNotFoundException, DuplicateItemException {
        assert target != null;
        assert toReplace != null;
        final int itemIndex = internalList.indexOf(target);
        if (itemIndex == -1) {
            throw new ItemNotFoundException();
        }
        if (contains(toReplace)) {
            throw new DuplicateItemException();
        }
        internalList.set(itemIndex, toReplace);
    }
    //@@author 
    
    /**
     * Removes the equivalent item from the list.
     *
     * @throws ItemNotFoundException if no such item could be found in the list.
     */
    public boolean remove(ReadOnlyItem toRemove) throws ItemNotFoundException {
        assert toRemove != null;
        final boolean itemFoundAndDeleted = internalList.remove(toRemove);
        if (!itemFoundAndDeleted) {
            throw new ItemNotFoundException();
        }
        return itemFoundAndDeleted;
    }
    
    public void setDone(ReadOnlyItem toEdit) throws ItemNotFoundException {
        assert toEdit != null;
        if (internalList.contains(toEdit)) {
            int idx = internalList.indexOf(toEdit);
            internalList.get(idx).setDone();
        } else {
        	throw new ItemNotFoundException();
        }
    }
    
    public void setUndone(ReadOnlyItem toEdit) throws ItemNotFoundException {
        assert toEdit != null;
        if (internalList.contains(toEdit)) {
            int idx = internalList.indexOf(toEdit);
            internalList.get(idx).setUndone();
        } else {
        	throw new ItemNotFoundException();
        }
    }

    public ObservableList<Item> getInternalList() {
        return internalList;
    }

    @Override
    public Iterator<Item> iterator() {
        return internalList.iterator();
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof UniqueItemList // instanceof handles nulls
                && this.internalList.equals(
                ((UniqueItemList) other).internalList));
    }

    @Override
    public int hashCode() {
        return internalList.hashCode();
    }
}
