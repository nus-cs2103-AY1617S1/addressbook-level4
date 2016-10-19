package jym.manager.model;


import java.util.List;

import jym.manager.model.tag.Tag;
import jym.manager.model.tag.UniqueTagList;
import jym.manager.model.task.ReadOnlyTask;
import jym.manager.model.task.UniqueTaskList;

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
