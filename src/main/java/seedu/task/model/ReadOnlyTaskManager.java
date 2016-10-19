package seedu.task.model;


import seedu.task.model.person.ReadOnlyTask;
import seedu.task.model.person.UniqueTaskList;
import seedu.task.model.tag.Tag;
import seedu.task.model.tag.UniqueTagList;

import java.util.List;

/**
 * Unmodifiable view of an task manager
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
