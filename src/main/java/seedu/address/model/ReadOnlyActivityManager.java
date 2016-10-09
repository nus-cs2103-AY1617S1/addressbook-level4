package seedu.address.model;


import seedu.address.model.activity.Activity;
import seedu.address.model.activity.ActivityList;
import seedu.address.model.tag.Tag;
import seedu.address.model.tag.UniqueTagList;

import java.util.List;

/**
 * Unmodifiable view of an address book
 */
public interface ReadOnlyActivityManager {

    UniqueTagList getUniqueTagList();

    ActivityList getActivityList();

    /**
     * Returns an unmodifiable view of persons list
     */
    List<Activity> getListActivity();

    /**
     * Returns an unmodifiable view of tags list
     */
    List<Tag> getTagList();

}
