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
  
    //@@author A0140060A-reused
    /** Deletes the given item. */
    void deleteItem(ReadOnlyItem target, String actionTaken) throws UniqueItemList.ItemNotFoundException;

    /** Adds the given item */
    void addItem(Item item, String actionTaken) throws UniqueItemList.DuplicateItemException;

    //@@author A0140060A
    /** Replaces the given item */
    void replaceItem(ReadOnlyItem target, Item toReplace, String actionTaken) throws UniqueItemList.ItemNotFoundException, UniqueItemList.DuplicateItemException;
    //@@author 
    
    /** Returns the filtered item list as an {@code UnmodifiableObservableList<ReadOnlyItem>} */
    UnmodifiableObservableList<ReadOnlyItem> getFilteredItemList();

    /** Updates the filter of the filtered person list to show all items */
    void updateFilteredListToShowAll();
    
    //@@author A0140060A-reused
    /** Updates the filter of the filtered item list to filter by the given keywords*/
    void updateFilteredItemList(Set<String> keywords);
    
    //@@author A0140060A
    /** Updates the filter of the filtered person list to show all not done (uncompleted) items */
    void updateFilteredListToShowNotDone();
    
    //@@author A0135792X
    /**Updates the filter of the filtered item list to filter by task */
	void updateFilteredListToShowTask();
	
	/**Updates the filter of the filtered item list to filter by deadline */
	void updateFilteredListToShowDeadline();
	
	/**Updates the filter of the filtered item list to filter by event */
	void updateFilteredListToShowEvent();
	//@@author 
	
	
    /** Sets item as done */
    void setDone(ReadOnlyItem target, String actionTaken) throws UniqueItemList.ItemNotFoundException;
    
    /** Sets item as undone */
    void setUndone(ReadOnlyItem target, String actionTaken) throws UniqueItemList.ItemNotFoundException;
    
    /** Undo last action that changed todo list */
    String undoAction();
    
    /** Redo last undone action that changed todo list */
    String redoAction();

}
