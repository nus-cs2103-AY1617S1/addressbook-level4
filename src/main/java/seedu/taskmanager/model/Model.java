package seedu.taskmanager.model;

import seedu.taskmanager.commons.core.UnmodifiableObservableList;
import seedu.taskmanager.model.item.Item;
import seedu.taskmanager.model.item.ReadOnlyItem;
import seedu.taskmanager.model.item.UniqueItemList;

import java.util.Set;

/**
 * The API of the Model component.
 */
public interface Model {
    /** Clears existing backing model and replaces with the provided new data. */
    void resetData(ReadOnlyTaskManager newData, String actionTaken);

    /** Returns the TaskManager */
    ReadOnlyTaskManager getTaskManager();

    /** Deletes the given item. */
    void deleteItem(ReadOnlyItem target, String actionTaken) throws UniqueItemList.ItemNotFoundException;

    /** Adds the given item */
    void addItem(Item item, String actionTaken) throws UniqueItemList.DuplicateItemException;

    /** Replaces the given item */
    void replaceItem(ReadOnlyItem target, Item toReplace, String actionTaken) throws UniqueItemList.ItemNotFoundException, UniqueItemList.DuplicateItemException;
    
    /** Returns the filtered item list as an {@code UnmodifiableObservableList<ReadOnlyItem>} */
    UnmodifiableObservableList<ReadOnlyItem> getFilteredItemList();

    /** Updates the filter of the filtered person list to show all items */
    void updateFilteredListToShowAll();

    /** Updates the filter of the filtered item list to filter by the given keywords*/
    void updateFilteredItemList(Set<String> keywords);
    
    /**Updates the filter of the filtered item list to filter by task */
	void updateFilteredListToShowTask();
	
	/**Updates the filter of the filtered item list to filter by deadline */
	void updateFilteredListToShowDeadline();
	
	/**Updates the filter of the filtered item list to filter by event */
	void updateFilteredListToShowEvent();
	
    /** Sets item as done */
    void setDone(ReadOnlyItem target, String actionTaken) throws UniqueItemList.ItemNotFoundException;
    
    /** Sets item as undone */
    void setUndone(ReadOnlyItem target, String actionTaken) throws UniqueItemList.ItemNotFoundException;
    
    /** Undo last action that changed todo list */
    String undoAction();
    
    /** Redo last undone action that changed todo list */
    String redoAction();

}
