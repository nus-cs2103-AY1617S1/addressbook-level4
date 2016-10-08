package seedu.inbx0.model;


import java.util.List;

import seedu.inbx0.model.task.ReadOnlyTask;
import seedu.inbx0.model.task.UniqueTaskList;
import seedu.inbx0.model.tag.Tag;
import seedu.inbx0.model.tag.UniqueTagList;

/**
 * Unmodifiable view of an address book
 */
public interface ReadOnlyTaskList {

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
