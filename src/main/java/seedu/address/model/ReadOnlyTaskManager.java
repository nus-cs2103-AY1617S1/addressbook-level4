package seedu.address.model;


import seedu.address.model.person.ReadOnlyPerson;
import seedu.address.model.person.UniquePersonList;
import seedu.address.model.tag.Tag;
import seedu.address.model.tag.UniqueTagList;

import java.util.List;

/**
 * Unmodifiable view of a task manager
 */
public interface ReadOnlyTaskManager {

    UniqueTagList getUniqueTagList();

    UniquePersonList getUniqueTaskList();

    /**
     * Returns an unmodifiable view of tasks list
     */
    List<ReadOnlyPerson> getTaskList();

    /**
     * Returns an unmodifiable view of tags list
     */
    List<Tag> getTagList();

}
