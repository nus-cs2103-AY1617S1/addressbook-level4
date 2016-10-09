package tars.model;


import tars.model.task.ReadOnlyTask;
import tars.model.task.UniqueTaskList;
import tars.model.tag.Tag;
import tars.model.tag.UniqueTagList;

import java.util.List;

/**
 * Unmodifiable view of tars
 */
public interface ReadOnlyTars {

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
