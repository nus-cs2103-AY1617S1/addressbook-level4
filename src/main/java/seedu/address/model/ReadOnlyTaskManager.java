package seedu.address.model;


import seedu.address.model.task.ReadOnlyTask;
import seedu.address.model.task.UniqueTaskList;
import seedu.address.model.tag.Tag;
import seedu.address.model.tag.UniqueTagList;

import java.util.List;

/**
 * Unmodifiable view of an address book
 */
public interface ReadOnlyTaskManager {

    UniqueTagList getUniqueTagList();

    UniqueTaskList getUniqueTaskList();

    /**
     * Returns an unmodifiable view of the list of all tasks
     */
    List<ReadOnlyTask> getTaskList();
    
    /**
     * Returns an unmodifiable view of the list of tasks for today
     */
    List<ReadOnlyTask> getTodayTasks();

	/**
     * Returns an unmodifiable view of the list of tasks for tomorrow
     */
    List<ReadOnlyTask> getTomorrowTasks();

	/**
     * Returns an unmodifiable view of the list of tasks for the next 7 days
     */
    List<ReadOnlyTask> getIn7DaysTasks();

	/**
     * Returns an unmodifiable view of the list of tasks for the next 30 days
     */
    List<ReadOnlyTask> getIn30DaysTasks();

	/**
     * Returns an unmodifiable view of the list of all someday tasks
     */
    List<ReadOnlyTask> getSomedayTasks();

    /**
     * Returns an unmodifiable view of tags list
     */
    List<Tag> getTagList();

}
