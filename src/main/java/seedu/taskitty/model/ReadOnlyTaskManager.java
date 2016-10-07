package seedu.taskitty.model;


import seedu.taskitty.model.tag.Tag;
import seedu.taskitty.model.tag.UniqueTagList;
import seedu.taskitty.model.task.ReadOnlyTask;
import seedu.taskitty.model.task.UniqueTaskList;

import java.util.List;

/**
 * Unmodifiable view of a task manager
 */
public interface ReadOnlyTaskManager {

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
