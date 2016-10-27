package seedu.oneline.model;


import java.util.List;
import java.util.Map;

import seedu.oneline.model.tag.Tag;
import seedu.oneline.model.tag.TagColor;
import seedu.oneline.model.tag.TagColorMap;
import seedu.oneline.model.tag.UniqueTagList;
import seedu.oneline.model.task.ReadOnlyTask;
import seedu.oneline.model.task.Task;
import seedu.oneline.model.task.TaskName;
import seedu.oneline.model.task.UniqueTaskList;

/**
 * Unmodifiable view of an task book
 */
public interface ReadOnlyTaskBook {

    UniqueTagList getUniqueTagList();

    UniqueTaskList getUniqueTaskList();
    
    TagColorMap getTagColorMap();
    
    /**
     * Returns an unmodifiable view of tasks list
     */
    List<ReadOnlyTask> getTaskList();

    /**
     * Returns an unmodifiable view of tags list
     */
    List<Tag> getTagList();
    
    /**
     * Returns an unmodifiable view of tag color map
     */
    Map<Tag, TagColor> getInternalTagColorMap();

}
