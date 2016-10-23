package seedu.taskscheduler.model;


import seedu.taskscheduler.model.tag.Tag;
import seedu.taskscheduler.model.tag.UniqueTagList;
import seedu.taskscheduler.model.task.ReadOnlyTask;
import seedu.taskscheduler.model.task.UniqueTaskList;

import java.util.List;

/**
 * Unmodifiable view of an Task Scheduler
 */
public interface ReadOnlyTaskScheduler {

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
