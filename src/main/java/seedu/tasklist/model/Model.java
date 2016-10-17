package seedu.tasklist.model;

import java.util.List;
import java.util.Set;

import seedu.tasklist.commons.core.UnmodifiableObservableList;
import seedu.tasklist.model.tag.UniqueTagList;
import seedu.tasklist.model.task.EndTime;
import seedu.tasklist.model.task.Priority;
import seedu.tasklist.model.task.ReadOnlyTask;
import seedu.tasklist.model.task.StartTime;
import seedu.tasklist.model.task.Task;
import seedu.tasklist.model.task.TaskDetails;
import seedu.tasklist.model.task.UniqueTaskList;
import seedu.tasklist.model.task.UniqueTaskList.DuplicateTaskException;
import seedu.tasklist.model.task.UniqueTaskList.TaskNotFoundException;

/**
 * The API of the Model component.
 */
public interface Model {
    /** Clears existing backing model and replaces with the provided new data. */
    void resetData(ReadOnlyTaskList newData);

    /** Returns the TaskList */
    ReadOnlyTaskList getTaskList();

    /** Deletes the given task. */
    void deleteTask(ReadOnlyTask target) throws UniqueTaskList.TaskNotFoundException;

    /** Adds the given task */
    void addTask(Task task) throws UniqueTaskList.DuplicateTaskException;
    
    /** Marks the given task as complete */
    void markTaskAsComplete(ReadOnlyTask target) throws UniqueTaskList.TaskNotFoundException;

    /** Returns the filtered task list as an {@code UnmodifiableObservableList<ReadOnlyPerson>} */
    UnmodifiableObservableList<ReadOnlyTask> getFilteredTaskList();

    /** Updates the filter of the filtered task list to show all persons */
    void updateFilteredListToShowAll();

    /** Updates the filter of the filtered task list to filter by the given keywords */
    void updateFilteredTaskList(Set<String> keywords);

    Set<String> getKeywordsFromList(List<ReadOnlyTask> matchingTasks);

    /** Updates the filter of the filtered task list to only show incomplete */
	void updateFilteredListToShowIncomplete();

    UnmodifiableObservableList<Task> getModifiableTaskList();

    void updateFilteredList();

    /** Updates the filter of the filtered task list to only show complete */
    void updateFilteredListToShowComplete();
    
    /** Updates the filter of the filtered task list to only certain priority */
    public void updateFilteredListToShowPriority(String priority);
    
    /** Updates the filter of the filtered task list to only certain date */
    public void updateFilteredListToShowDate(String date);
    
    /** Updates the filter of the filtered task list to only floating tasks */
    public void updateFilteredListToShowFloating();
    
    /** Updates the filter of the filtered task list to only overdue tasks */
    public void updateFilteredListToShowOverDue();

    void markTaskAsIncomplete(ReadOnlyTask task) throws TaskNotFoundException;

    void updateTask(Task taskToUpdate, TaskDetails taskDetails, StartTime startTime, EndTime endTime, Priority priority,
            UniqueTagList tags) throws DuplicateTaskException;

    void addTaskUndo(Task task) throws DuplicateTaskException;

    void updateTaskUndo(Task taskToUpdate, TaskDetails taskDetails, StartTime startTime, EndTime endTime,
            Priority priority, UniqueTagList tags) throws DuplicateTaskException;

    void deleteTaskUndo(ReadOnlyTask target) throws TaskNotFoundException;

}
