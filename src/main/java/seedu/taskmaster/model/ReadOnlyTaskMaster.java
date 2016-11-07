package seedu.taskmaster.model;


import seedu.taskmaster.model.tag.Tag;
import seedu.taskmaster.model.tag.UniqueTagList;
import seedu.taskmaster.model.task.ReadOnlyTask;
import seedu.taskmaster.model.task.TaskOccurrence;
import seedu.taskmaster.model.task.UniqueTaskList;

import java.util.List;

/**
 * Unmodifiable view of an tag list
 */
public interface ReadOnlyTaskMaster {

    UniqueTagList getUniqueTagList();

    UniqueTaskList getUniqueTaskList();

    /**
     * Returns an unmodifiable view of tasks list
     */
    List<ReadOnlyTask> getTaskList();
    List<TaskOccurrence> getTaskOccurrenceList();

    
    /**
     * Returns an unmodifiable view of tags list
     */
    List<Tag> getTagList();


}
