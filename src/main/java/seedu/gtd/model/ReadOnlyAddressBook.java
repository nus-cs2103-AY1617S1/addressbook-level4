package seedu.gtd.model;


import java.util.List;

import seedu.gtd.model.tag.Tag;
import seedu.gtd.model.tag.UniqueTagList;
import seedu.gtd.model.task.ReadOnlyTask;
import seedu.gtd.model.task.UniqueTaskList;

/**
 * Unmodifiable view of an task list
 */
public interface ReadOnlyAddressBook {

    UniqueTagList getUniqueTagList();

    UniqueTaskList getUniqueTaskList();

    /**
     * Returns an unmodifiable view of tasks list
     */
    List<ReadOnlyTask> getTaskList();

    /**
     * Returns an unmodifiable view of tags list
     */
    List<Tag> getTagList();

}
