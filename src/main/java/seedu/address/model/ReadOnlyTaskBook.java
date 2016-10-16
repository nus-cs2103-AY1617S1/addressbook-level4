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
    UniqueTaskList getUniquePersonList();
    UniqueTaskList getUniqueDeadlineList();
    UniqueTaskList getUniqueTodoList();
    /**
     * Returns an unmodifiable view of persons list
     */
    List<ReadOnlyTask> getPersonList();
    
    List<ReadOnlyTask> getDeadlineList();

    List<ReadOnlyTask> getTodoList();

    /**
     * Returns an unmodifiable view of tags list
     */
    List<Tag> getTagList();

}
