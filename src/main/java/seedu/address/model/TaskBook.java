package seedu.address.model;

import javafx.collections.ObservableList;
import seedu.address.model.item.Item;
import seedu.address.model.item.UniqueItemList;
import seedu.address.model.item.ReadOnlyItem;
import seedu.address.model.item.FloatingTask;


import java.util.*;
import java.util.stream.Collectors;

/**
 * Wraps all data at the address-book level
 * Duplicates are not allowed (by .equals comparison)
 */
public class TaskBook implements ReadOnlyTaskBook {

    private final UniqueItemList items;
    {
        items = new UniqueItemList();
    }

    public TaskBook() {}

    /**
     * Persons and Tags are copied into this addressbook
     */
    public TaskBook(ReadOnlyTaskBook toBeCopied) {
        this(toBeCopied.getUniqueItemList());
    }

    /**
     * Persons and Tags are copied into this addressbook
     */
    public TaskBook(UniqueItemList items) {
        resetData(items.getInternalList());
    }

    public static ReadOnlyTaskBook getEmptyAddressBook() {
        return new TaskBook();
    }

//// list overwrite operations

    public ObservableList<Item> getItems() {
        return items.getInternalList();
    }

    public void setItems(List<Item> items) {
        this.items.getInternalList().setAll(items);
    }

    public void resetData(Collection<? extends ReadOnlyItem> newItems) {
        setItems(newItems.stream().map(FloatingTask::new).collect(Collectors.toList()));
    }

    public void resetData(ReadOnlyTaskBook newData) {
        resetData(newData.getItemList());
    }

//// person-level operations

    /**
     * Adds a person to the address book.
     * Also checks the new person's tags and updates {@link #tags} with any new tags found,
     * and updates the Tag objects in the person to point to those in {@link #tags}.
     *
     * @throws UniquePersonList.DuplicatePersonException if an equivalent person already exists.
     */
    public void addItem(Item i) throws UniqueItemList.DuplicateItemException {
        items.add(i);
    }


    public boolean removePerson(ReadOnlyItem key) throws UniqueItemList.ItemNotFoundException {
        if (items.remove(key)) {
            return true;
        } else {
            throw new UniqueItemList.ItemNotFoundException();
        }
    }

//// util methods

    @Override
    public String toString() {
        return items.getInternalList().size() + " items";
        // TODO: refine later
    }

    @Override
    public List<ReadOnlyItem> getItemList() {
        return Collections.unmodifiableList(items.getInternalList());
    }

    @Override
    public UniqueItemList getUniqueItemList() {
        return this.items;
    }

     @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof TaskBook // instanceof handles nulls
                && this.items.equals(((TaskBook) other).items));
    }

    @Override
    public int hashCode() {
        // use this method for custom fields hashing instead of implementing your own
        return Objects.hash(items);
    }
}
