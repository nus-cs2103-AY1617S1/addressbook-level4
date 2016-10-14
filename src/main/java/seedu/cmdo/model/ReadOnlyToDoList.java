package seedu.cmdo.model;


import java.util.List;

import seedu.cmdo.model.tag.Tag;
import seedu.cmdo.model.tag.UniqueTagList;
import seedu.cmdo.model.task.ReadOnlyTask;
import seedu.cmdo.model.task.UniqueTaskList;

/**
 * Unmodifiable view of a To Do List
 */
public interface ReadOnlyToDoList {

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
