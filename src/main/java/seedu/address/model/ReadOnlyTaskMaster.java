package seedu.address.model;


import seedu.address.model.tag.Tag;
import seedu.address.model.tag.UniqueTagList;
import seedu.address.model.task.ReadOnlyTask;
import seedu.address.model.task.TaskComponent;
import seedu.address.model.task.UniqueTaskList;
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
    List<TaskComponent> getTaskComponentList();

    
    /**
     * Returns an unmodifiable view of tags list
     */
    List<Tag> getTagList();


}
