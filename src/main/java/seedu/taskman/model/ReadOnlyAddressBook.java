package seedu.taskman.model;


import seedu.taskman.model.tag.Tag;
import seedu.taskman.model.tag.UniqueTagList;
import seedu.taskman.model.task.ReadOnlyTask;
import seedu.taskman.model.task.UniqueTaskList;

import java.util.List;

/**
 * Unmodifiable view of an address book
 */
public interface ReadOnlyAddressBook {

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
