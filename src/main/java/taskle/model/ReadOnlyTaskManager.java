package taskle.model;


import java.util.List;

import taskle.model.person.ReadOnlyTask;
import taskle.model.person.UniqueTaskList;
import taskle.model.tag.Tag;
import taskle.model.tag.UniqueTagList;

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