package harmony.mastermind.model;


import java.util.List;


import harmony.mastermind.model.tag.Tag;
import harmony.mastermind.model.tag.UniqueTagList;
import harmony.mastermind.model.task.ArchiveTaskList;
import harmony.mastermind.model.task.ReadOnlyTask;
import harmony.mastermind.model.task.UniqueTaskList;

//@@author A0124797R
/**
 * Unmodifiable view of an task manager
 * 
 */
public interface ReadOnlyTaskManager {

    UniqueTagList getUniqueTagList();

    UniqueTaskList getUniqueTaskList();

    UniqueTaskList getUniqueFloatingTaskList();

    UniqueTaskList getUniqueEventList();

    UniqueTaskList getUniqueDeadlineList();
    
    ArchiveTaskList getUniqueArchiveList();

    /** Returns an unmodifiable view of tasks list */
    List<ReadOnlyTask> getTaskList();

    /** Returns an unmodifiable view of tasks list */
    List<ReadOnlyTask> getFloatingTaskList();

    /** Returns an unmodifiable view of tasks list*/
    List<ReadOnlyTask> getEventList();

    /** Returns an unmodifiable view of tasks list */
    List<ReadOnlyTask> getDeadlineList();
    
    /** Returns an unmodifiable view of archive list */
    List<ReadOnlyTask> getArchiveList();

    /** Returns an unmodifiable view of tags list */
    List<Tag> getTagList();




}
