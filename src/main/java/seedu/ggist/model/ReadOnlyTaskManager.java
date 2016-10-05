package seedu.ggist.model;


import java.util.List;

import seedu.ggist.model.tag.Tag;
import seedu.ggist.model.tag.UniqueTagList;
import seedu.ggist.model.task.ReadOnlyTask;
import seedu.ggist.model.task.UniquePersonList;

/**
 * Unmodifiable view of an address book
 */
public interface ReadOnlyTaskManager {

    UniqueTagList getUniqueTagList();

    UniquePersonList getUniquePersonList();

    /**
     * Returns an unmodifiable view of persons list
     */
    List<ReadOnlyTask> getPersonList();

    /**
     * Returns an unmodifiable view of tags list
     */
    List<Tag> getTagList();

}
