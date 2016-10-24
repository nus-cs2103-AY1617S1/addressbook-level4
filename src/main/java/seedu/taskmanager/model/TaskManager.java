package seedu.taskmanager.model;

import javafx.collections.ObservableList;
import seedu.taskmanager.model.item.Item;
import seedu.taskmanager.model.item.ReadOnlyItem;
import seedu.taskmanager.model.item.UniqueItemList;
import seedu.taskmanager.model.item.UniqueItemList.ItemNotFoundException;
import seedu.taskmanager.model.tag.Tag;
import seedu.taskmanager.model.tag.UniqueTagList;

import java.util.*;
import java.util.stream.Collectors;

/**
 * Wraps all data at the task-manager level
 * Duplicates are not allowed (by .equals comparison)
 */
public class TaskManager implements ReadOnlyTaskManager {

    private final UniqueItemList items;
    private final UniqueTagList tags;

    {
        items = new UniqueItemList();
        tags = new UniqueTagList();
    }

    public TaskManager() {}

    /**
     * Items and Tags are copied into this Task Manager
     */
    public TaskManager(ReadOnlyTaskManager toBeCopied) {
        this(toBeCopied.getUniqueItemList(), toBeCopied.getUniqueTagList());
    }

    /**
     * Items and Tags are copied into this Task Manager
     */
    public TaskManager(UniqueItemList items, UniqueTagList tags) {
        resetData(items.getInternalList(), tags.getInternalList());
    }

    public static ReadOnlyTaskManager getEmptyTaskManager() {
        return new TaskManager();
    }

//// list overwrite operations

    public ObservableList<Item> getItems() {
        return items.getInternalList();
    }

    public void setItems(List<Item> items) {
        this.items.getInternalList().setAll(items);
    }

    public void setTags(Collection<Tag> tags) {
        this.tags.getInternalList().setAll(tags);
    }

    public void resetData(Collection<? extends ReadOnlyItem> newItems, Collection<Tag> newTags) {
        setItems(newItems.stream().map(Item::new).collect(Collectors.toList()));
        setTags(newTags);
    }

    public void resetData(ReadOnlyTaskManager newData) {
        resetData(newData.getItemList(), newData.getTagList());
    }

//// item-level operations

    /**
     * Adds an item to the task manager.
     * Also checks the new item's tags and updates {@link #tags} with any new tags found,
     * and updates the Tag objects in the item to point to those in {@link #tags}.
     *
     * @throws UniqueItemList.DuplicateItemException if an equivalent item already exists.
     */
    public void addItem(Item p) throws UniqueItemList.DuplicateItemException {
        syncTagsWithMasterList(p);
        items.add(p);
    }
    
    //@@author A0140060A
    /**
     * Edits an item in the task manager.
     * Also checks the new item's tags and updates {@link #tags} with any new tags found,
     * and updates the Tag objects in the item to point to those in {@link #tags}.
     *     
     * @throws ItemNotFoundException if no such item could be found in the task manager.
     * @throws UniqueItemList.DuplicateItemException if an equivalent item already exists.
     */
    public void replaceItem(ReadOnlyItem target, Item toReplace) 
            throws UniqueItemList.ItemNotFoundException, UniqueItemList.DuplicateItemException {
        syncTagsWithMasterList(toReplace);
        items.replace(target, toReplace);
    }
    //@@author 

    /**
     * Ensures that every tag in this item:
     *  - exists in the master list {@link #tags}
     *  - points to a Tag object in the master list
     */
    private void syncTagsWithMasterList(Item item) {
        final UniqueTagList itemTags = item.getTags();
        tags.mergeFrom(itemTags);

        // Create map with values = tag object references in the master list
        final Map<Tag, Tag> masterTagObjects = new HashMap<>();
        for (Tag tag : tags) {
            masterTagObjects.put(tag, tag);
        }

        // Rebuild the list of item tags using references from the master list
        final Set<Tag> commonTagReferences = new HashSet<>();
        for (Tag tag : itemTags) {
            commonTagReferences.add(masterTagObjects.get(tag));
        }
        item.setTags(new UniqueTagList(commonTagReferences));
    }

    public boolean removeItem(ReadOnlyItem key) throws UniqueItemList.ItemNotFoundException {
        if (items.remove(key)) {
            return true;
        } else {
            throw new UniqueItemList.ItemNotFoundException();
        }
    }
    
    /**
     * Set Item as done
     */
    public void setDone(ReadOnlyItem key) throws UniqueItemList.ItemNotFoundException {
        items.setDone(key);
    }
    
    /**
     * Set Item as undone
     */
    public void setUndone(ReadOnlyItem key) throws UniqueItemList.ItemNotFoundException {
        items.setUndone(key);
    }

//// tag-level operations

    public void addTag(Tag t) throws UniqueTagList.DuplicateTagException {
        tags.add(t);
    }

//// util methods

    @Override
    public String toString() {
        return items.getInternalList().size() + " items, " + tags.getInternalList().size() +  " tags";
        // TODO: refine later
    }

    @Override
    public List<ReadOnlyItem> getItemList() {
        return Collections.unmodifiableList(items.getInternalList());
    }

    @Override
    public List<Tag> getTagList() {
        return Collections.unmodifiableList(tags.getInternalList());
    }

    @Override
    public UniqueItemList getUniqueItemList() {
        return this.items;
    }

    @Override
    public UniqueTagList getUniqueTagList() {
        return this.tags;
    }


    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof TaskManager // instanceof handles nulls
                && this.items.equals(((TaskManager) other).items)
                && this.tags.equals(((TaskManager) other).tags));
    }

    @Override
    public int hashCode() {
        // use this method for custom fields hashing instead of implementing your own
        return Objects.hash(items, tags);
    }
}
