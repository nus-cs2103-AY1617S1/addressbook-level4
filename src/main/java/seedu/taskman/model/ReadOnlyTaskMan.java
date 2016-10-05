package seedu.taskman.model;


import seedu.taskman.model.tag.Tag;
import seedu.taskman.model.tag.UniqueTagList;
import seedu.taskman.model.task.ReadOnlyTask;
import seedu.taskman.model.task.UniqueTaskList;

import java.util.List;

/**
 * Unmodifiable view of an task man
 */
public interface ReadOnlyTaskMan {

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
