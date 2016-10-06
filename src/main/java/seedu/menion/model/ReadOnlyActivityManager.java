package seedu.menion.model;


import seedu.menion.model.tag.Tag;
import seedu.menion.model.tag.UniqueTagList;
import seedu.menion.model.task.ReadOnlyTask;
import seedu.menion.model.task.UniqueTaskList;

import java.util.List;

/**
 * Unmodifiable view of an task manager
 */
public interface ReadOnlyActivityManager {

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
