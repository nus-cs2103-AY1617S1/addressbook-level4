package seedu.address.model;


import java.util.List;

import seedu.address.model.tag.Tag;
import seedu.address.model.tag.UniqueTagList;
import seedu.address.model.task.ReadOnlyTask;
import seedu.address.model.task.UniqueTaskList;

/**
 * Unmodifiable view of an address book
 */
public interface ReadOnlyTaskList {

    UniqueTagList getUniqueTagList();

    UniqueTaskList getUniquePersonList();

    /**
     * Returns an unmodifiable view of persons list
     */
    List<ReadOnlyTask> getPersonList();

    /**
     * Returns an unmodifiable view of tags list
     */
    List<Tag> getTagList();

}
