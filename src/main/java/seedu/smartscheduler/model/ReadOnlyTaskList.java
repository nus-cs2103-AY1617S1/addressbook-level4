package seedu.smartscheduler.model;


import java.util.List;

import seedu.smartscheduler.model.tag.Tag;
import seedu.smartscheduler.model.tag.UniqueTagList;
import seedu.smartscheduler.model.task.ReadOnlyTask;
import seedu.smartscheduler.model.task.UniqueTaskList;

/**
 * Unmodifiable view of an address book
 */
public interface ReadOnlyTaskList {

    UniqueTagList getUniqueTagList();

    UniqueTaskList getUniquePersonList();

    /**
     * Returns an unmodifiable view of persons list
     */
    List<ReadOnlyTask> getPersonList();

    /**
     * Returns an unmodifiable view of tags list
     */
    List<Tag> getTagList();

}
