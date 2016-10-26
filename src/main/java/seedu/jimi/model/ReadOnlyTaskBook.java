package seedu.jimi.model;


import java.util.List;

import seedu.jimi.model.tag.Tag;
import seedu.jimi.model.tag.UniqueTagList;
import seedu.jimi.model.task.ReadOnlyTask;
import seedu.jimi.model.task.UniqueTaskList;

/**
 * @@author A0148040R
 * Unmodifiable view of Jimi's task book.
 */
public interface ReadOnlyTaskBook {

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
