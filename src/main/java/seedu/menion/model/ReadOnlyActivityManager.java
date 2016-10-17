package seedu.menion.model;


import seedu.menion.model.activity.ReadOnlyActivity;
import seedu.menion.model.activity.UniqueActivityList;
import seedu.menion.model.tag.Tag;
import seedu.menion.model.tag.UniqueTagList;

import java.util.List;

/**
 * Unmodifiable view of an task manager
 */
public interface ReadOnlyActivityManager {

    UniqueTagList getUniqueTagList();

    UniqueActivityList getUniqueTaskList();
    
    UniqueActivityList getUniqueFloatingTaskList();
    
    UniqueActivityList getUniqueEventList();

    /**
     * Returns an unmodifiable view of tasks list
     */
    List<ReadOnlyActivity> getTaskList();

    /**
     * Returns an unmodifiable view of tags list
     */
    List<Tag> getTagList();
    
    
    /**
     * Returns an unmodifiable view of floating tasks list
     */
    List<ReadOnlyActivity> getFloatingTaskList();
    
    /**
     * Returns an unmodifiable view of events list
     */
    List<ReadOnlyActivity> getEventList();

}
