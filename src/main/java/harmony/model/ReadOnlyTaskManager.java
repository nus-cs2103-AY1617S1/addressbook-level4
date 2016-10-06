package harmony.model;


import java.util.List;

import harmony.model.tag.Tag;
import harmony.model.tag.UniqueTagList;
import harmony.model.task.ReadOnlyTask;
import harmony.model.task.UniqueTaskList;

/**
 * Unmodifiable view of an address book
 */
public interface ReadOnlyTaskManager {

    UniqueTagList getUniqueTagList();

    UniqueTaskList getUniqueTaskList();

    /**
     * Returns an unmodifiable view of persons list
     */
    List<ReadOnlyTask> getTaskList();

    /**
     * Returns an unmodifiable view of tags list
     */
    List<Tag> getTagList();

}
