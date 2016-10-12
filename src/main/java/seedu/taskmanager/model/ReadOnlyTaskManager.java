package seedu.taskmanager.model;


import seedu.taskmanager.model.item.ReadOnlyItem;
import seedu.taskmanager.model.item.UniqueItemList;
import seedu.taskmanager.model.tag.Tag;
import seedu.taskmanager.model.tag.UniqueTagList;

import java.util.List;

/**
 * Unmodifiable view of an address book
 */
public interface ReadOnlyTaskManager {

    UniqueTagList getUniqueTagList();

    UniqueItemList getUniqueItemList();

    /**
     * Returns an unmodifiable view of persons list
     */
    List<ReadOnlyItem> getItemList();

    /**
     * Returns an unmodifiable view of tags list
     */
    List<Tag> getTagList();

}
