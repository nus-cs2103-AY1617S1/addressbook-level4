package seedu.address.model;


import seedu.address.model.tag.Tag;
import seedu.address.model.tag.UniqueTagList;
import seedu.address.model.task.ReadOnlyTask;
import seedu.address.model.task.UniqueTaskList;

import java.util.List;

/**
 * Unmodifiable view of an address book
 */
public interface ReadOnlyTaskBook {

    UniqueTagList getUniqueTagList();
    UniqueTaskList getUniqueEventList();
    UniqueTaskList getUniqueDeadlineList();
    UniqueTaskList getUniqueTodoList();
    /**
     * Returns an unmodifiable view of event, deadline, todo list
     */
    List<ReadOnlyTask> getEventList();
    
    List<ReadOnlyTask> getDeadlineList();

    List<ReadOnlyTask> getTodoList();

    /**
     * Returns an unmodifiable view of tags list
     */
    List<Tag> getTagList();

}
