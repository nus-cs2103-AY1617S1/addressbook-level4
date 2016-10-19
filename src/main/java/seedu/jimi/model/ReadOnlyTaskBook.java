package seedu.jimi.model;


import java.util.List;

import seedu.jimi.model.tag.Tag;
import seedu.jimi.model.tag.UniqueTagList;
import seedu.jimi.model.task.ReadOnlyTask;
import seedu.jimi.model.task.UniqueTaskList;

/**
 * Unmodifiable view of Jimi's task book.
 */
public interface ReadOnlyTaskBook {

    UniqueTagList getUniqueTagList();

    UniqueTaskList getUniqueTaskList();
    
    UniqueTaskList getUniqueDeadlineTaskList();
    
    UniqueTaskList getUniqueEventList();

    /**
     * Returns an unmodifiable view of tasks list
     */
    List<ReadOnlyTask> getTaskList();
    
    /**
     * Returns an unmodifiable view of deadlineTasks list
     */
    List<ReadOnlyTask> getDeadlineTaskList();
    
    /**
     * Returns an unmodifiable view of events list
     */
    List<ReadOnlyTask> getEventList();

    /**
     * Returns an unmodifiable view of tags list
     */
    List<Tag> getTagList();

}
