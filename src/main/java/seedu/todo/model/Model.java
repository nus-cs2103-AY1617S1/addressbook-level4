package seedu.todo.model;

import java.util.Set;

import seedu.todo.commons.core.UnmodifiableObservableList;
import seedu.todo.model.tag.Tag;
import seedu.todo.model.tag.UniqueTagList;
import seedu.todo.model.task.Priority;
import seedu.todo.model.task.ReadOnlyTask;
import seedu.todo.model.task.Task;
import seedu.todo.model.task.UniqueTaskList;

import java.time.LocalDateTime;

/**
 * The API of the Model component.
 */
public interface Model {
    
    /** Returns the ToDoList */
    ReadOnlyToDoList getToDoList();

    /** Clears existing backing model and replaces with the provided new data. */
    void resetData(ReadOnlyToDoList newData);

    /** Move model back to previous state */
    boolean undo();
    
    /** Add the given task */
    void addTask(Task task) throws UniqueTaskList.DuplicateTaskException;
    
    /** Deletes the given task. */
    void deleteTask(ReadOnlyTask target) throws UniqueTaskList.TaskNotFoundException;

    /** Retrieve the give task. */
    Task getTask(ReadOnlyTask target) throws UniqueTaskList.TaskNotFoundException;
    
    /** Update the given task */
    void updateTask(ReadOnlyTask oldTask, ReadOnlyTask newTask) throws UniqueTaskList.TaskNotFoundException;
    
    /** Add the given tags to the task*/
    void addTaskTags(ReadOnlyTask oldTask, UniqueTagList newTagList) throws UniqueTaskList.TaskNotFoundException;
       
    /** Remove the given tags to the task*/
    void deleteTaskTags(ReadOnlyTask oldTask, UniqueTagList newTagList) throws UniqueTaskList.TaskNotFoundException;
    
    /** Returns the filtered tasks list as an {@code UnmodifiableObservableList<ReadOnlyTask>} */
    UnmodifiableObservableList<ReadOnlyTask> getFilteredTaskList();

    /** Returns the filtered tasks list as an {@code UnmodifiableObservableList<ReadOnlyTask>} */
    UnmodifiableObservableList<ReadOnlyTask> getFilteredTodayTaskList();
    
    //@@author A0142421X
    /**Returns the filtered tag list as an {@code UnmodifiableObservableList<Tag>} */
    UnmodifiableObservableList<Tag> getUnmodifiableTagList();
    
    //@@author A0093896H
    /** Updates the filter of the filtered task list to show all tasks */
    void updateFilteredListToShowAll();
    
    /** Updates the filter of the filtered task list to show all completed tasks */
    void updateFilteredListToShowAllCompleted();

    /** Updates the filter of the filtered task list to show all not completed tasks */
    void updateFilteredListToShowAllNotCompleted();
    
    /** Updates the filter of the filtered task list to filter by the given keywords*/
    void updateFilteredTaskListByKeywords(Set<String> keywords);
    
    /** Updates the filter of the filtered task list to filter by the given tag name*/
    void updateFilteredTaskListByTag(String tagName);

    /** Updates the filter of the filtered task list to filter by the given date*/
    void updateFilteredTaskListOnDate(LocalDateTime datetime);
    
    /** Updates the filter of the filtered task list to filter by the given before date*/
    void updateFilteredTaskListBeforeDate(LocalDateTime datetime);
    
    /** Updates the filter of the filtered task list to filter by the given after date*/
    void updateFilteredTaskListAfterDate(LocalDateTime datetime);
    
    /** Updates the filter of the filtered task list to filter by the given from and till dates*/
    void updateFilteredTaskListFromTillDate(LocalDateTime fromDateTime, LocalDateTime tillDateTime);
    
    /** Updates the filter of the filtered task list to filter for today's date only */
    void updateFilteredTaskListTodayDate(LocalDateTime datetime);
    
    //@@author A0121643R
    /** updates the filter of the filtered task list to filter by the given priority level*/
    void updateFilteredTaskListByPriority(Priority priority);
}
