package tars.model;


import tars.model.task.ReadOnlyTask;
import tars.model.task.UniqueTaskList;
import tars.model.task.rsv.RsvTask;
import tars.model.task.rsv.UniqueRsvTaskList;
import tars.model.tag.Tag;
import tars.model.tag.UniqueTagList;

import java.util.List;

/**
 * Unmodifiable view of tars
 */
public interface ReadOnlyTars {

    UniqueTagList getUniqueTagList();

    UniqueTaskList getUniqueTaskList();
    
    UniqueRsvTaskList getUniqueRsvTaskList();

    /**
     * Returns an unmodifiable view of tasks list
     */
    List<ReadOnlyTask> getTaskList();
    
    /**
     * Returns an unmodifiable view of tasks list
     */
    List<RsvTask> getRsvTaskList();

    /**
     * Returns an unmodifiable view of tags list
     */
    List<Tag> getTagList();

}
