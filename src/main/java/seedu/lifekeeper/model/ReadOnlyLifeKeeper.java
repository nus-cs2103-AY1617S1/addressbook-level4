package seedu.lifekeeper.model;


import java.util.List;

import seedu.lifekeeper.model.activity.ReadOnlyActivity;
import seedu.lifekeeper.model.activity.UniqueActivityList;
import seedu.lifekeeper.model.tag.Tag;
import seedu.lifekeeper.model.tag.UniqueTagList;

/**
 * Unmodifiable view of an address book
 */
public interface ReadOnlyLifeKeeper {

    UniqueTagList getUniqueTagList();

    UniqueActivityList getUniqueActivityList();

    /**
     * Returns an unmodifiable view of activities list
     */
    List<ReadOnlyActivity> getActivityList();

    /**
     * Returns an unmodifiable view of tags list
     */
    List<Tag> getTagList();

}
