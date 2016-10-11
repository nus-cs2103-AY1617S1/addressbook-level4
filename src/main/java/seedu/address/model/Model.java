package seedu.address.model;

import seedu.address.commons.core.UnmodifiableObservableList;
import seedu.address.model.item.Item;
import seedu.address.model.item.ReadOnlyItem;
import seedu.address.model.item.UniquePersonList;

import java.util.Set;

/**
 * The API of the Model component.
 */
public interface Model {
    /** Clears existing backing model and replaces with the provided new data. */
    void resetData(ReadOnlyAddressBook newData);

    /** Returns the AddressBook */
    ReadOnlyAddressBook getAddressBook();

    /** Deletes the given item. */
    void deleteItem(ReadOnlyItem target) throws UniquePersonList.PersonNotFoundException;

    /** Adds the given item */
    void addItem(Item item) throws UniquePersonList.DuplicatePersonException;

    /** Replaces the given item */
    void replaceItem(ReadOnlyItem target, Item toReplace) throws UniquePersonList.PersonNotFoundException, UniquePersonList.DuplicatePersonException;
    
    /** Returns the filtered item list as an {@code UnmodifiableObservableList<ReadOnlyItem>} */
    UnmodifiableObservableList<ReadOnlyItem> getFilteredPersonList();

    /** Updates the filter of the filtered person list to show all items */
    void updateFilteredListToShowAll();

    /** Updates the filter of the filtered item list to filter by the given keywords*/
    void updateFilteredPersonList(Set<String> keywords);
    
    //editted these 2 lines below 
    /**Updates the filter of the filtered item list to filter by task */

	void updateFilteredListToShowTask();

}
