package seedu.stask.model;


import java.util.List;

import seedu.stask.model.tag.Tag;
import seedu.stask.model.tag.UniqueTagList;
import seedu.stask.model.task.ReadOnlyTask;
import seedu.stask.model.task.UniqueTaskList;

/**
 * Unmodifiable view of an address book
 */
public interface ReadOnlyTaskBook {

    UniqueTagList getUniqueTagList();

    UniqueTaskList getUniqueDatedTaskList();
    
    UniqueTaskList getUniqueUndatedTaskList();

    /**
     * Returns an unmodifiable view of persons list
     */
    List<ReadOnlyTask> getDatedTaskList();
    
    /**
     * Returns an unmodifiable view of persons list
     */
    List<ReadOnlyTask> getUndatedTaskList();

    /**
     * Returns an unmodifiable view of tags list
     */
    List<Tag> getTagList();

}
