package seedu.taskman.model;


import seedu.taskman.model.tag.Tag;
import seedu.taskman.model.tag.UniqueTagList;
import seedu.taskman.model.event.Activity;
import seedu.taskman.model.event.UniqueActivityList;

import java.util.List;

/**
 * Unmodifiable view of an task man
 */
public interface ReadOnlyTaskMan {

    UniqueTagList getUniqueTagList();

    UniqueActivityList getUniqueActivityList();

    /**
     * Returns an unmodifiable view of tasks list
     */
    List<Activity> getActivityList();

    /**
     * Returns an unmodifiable view of tags list
     */
    List<Tag> getTagList();

}
