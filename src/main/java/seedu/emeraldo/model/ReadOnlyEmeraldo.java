package seedu.emeraldo.model;


import seedu.emeraldo.model.tag.Tag;
import seedu.emeraldo.model.tag.UniqueTagList;
import seedu.emeraldo.model.task.ReadOnlyTask;
import seedu.emeraldo.model.task.UniquePersonList;

import java.util.List;

/**
 * Unmodifiable view of a task manager
 */
public interface ReadOnlyEmeraldo {

    UniqueTagList getUniqueTagList();

    UniquePersonList getUniqueTaskList();

    /**
     * Returns an unmodifiable view of tasks list
     */
    List<ReadOnlyTask> getTaskList();

    /**
     * Returns an unmodifiable view of tags list
     */
    List<Tag> getTagList();

}
