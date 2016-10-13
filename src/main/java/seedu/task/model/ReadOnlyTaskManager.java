package seedu.task.model;


import seedu.task.model.task.ReadOnlyTask;
import seedu.task.model.task.UniqueTaskList;
import seedu.task.model.tag.Tag;
import seedu.task.model.tag.UniqueTagList;

import java.text.ParseException;
import java.util.List;

/**
 * Unmodifiable view of an address book
 */
public interface ReadOnlyTaskManager {

    UniqueTagList getUniqueTagList();

    UniqueTaskList getUniqueTaskList() throws ParseException;

    /**
     * Returns an unmodifiable view of tasks list
     */
    List<ReadOnlyTask> getTaskList();

    /**
     * Returns an unmodifiable view of tags list
     */
    List<Tag> getTagList();

}
