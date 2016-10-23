package teamfour.tasc.model;

import teamfour.tasc.commons.core.UnmodifiableObservableList;
import teamfour.tasc.model.task.ReadOnlyTask;
import teamfour.tasc.model.task.Task;
import teamfour.tasc.model.task.UniqueTaskList;
import teamfour.tasc.model.task.UniqueTaskList.TaskNotFoundException;

import java.util.Date;
import java.util.Set;

/**
 * The API of the Model component.
 */
public interface Model {
    public static final String SORT_ORDER_BY_EARLIEST_FIRST = "earliest first";
    public static final String SORT_ORDER_BY_LATEST_FIRST = "latest first";
    public static final String SORT_ORDER_BY_A_TO_Z = "a-z";
    public static final String SORT_ORDER_BY_Z_TO_A = "z-a";

    /** Clears existing backing model and replaces with the provided new data */
    void resetData(ReadOnlyTaskList newData);

    /** Returns the TaskList */
    ReadOnlyTaskList getTaskList();

    /** Saves the current state of the TaskList into history */
    void saveTaskListHistory();
    
    /**
     * Precondition: numToUndo must be > 0.
     * Reverts the task list to the most recent past state
     * @return number of history states undone
     */
    int undoTaskListHistory(int numToUndo);
    
    /** Deletes the given task */
    void deleteTask(ReadOnlyTask target) throws UniqueTaskList.TaskNotFoundException;

    /** Adds the given task */
    void addTask(Task task) throws UniqueTaskList.DuplicateTaskException;

    /** Returns the filtered task list as an {@code UnmodifiableObservableList<ReadOnlyTask>} */
    UnmodifiableObservableList<ReadOnlyTask> getFilteredTaskList();

    /** Updates the filter of the filtered task list to show all tasks */
    void updateFilteredTaskToShowAll();

    /** Updates the filter of the filtered task list to filter by the given keywords */
    void updateFilteredTaskList(Set<String> keywords);

    /** Removes all filters of the filtered task list */
    void resetTaskListFilter();

    /**
     * Precondition: arguments are not null.
     * Adds the filter of the filtered task list by the given type
     */
    void addTaskListFilterByType(String type, boolean negated);

    /**
     * Precondition: arguments are not null.
     * Adds the filter of the filtered task list by the given deadline
     */
    void addTaskListFilterByDeadline(Date deadline, boolean negated);

    /**
     * Precondition: arguments are not null.
     * Adds the filter of the filtered task list by the given start time
     */
    void addTaskListFilterByStartTime(Date startTime, boolean negated);

    /**
     * Precondition: arguments are not null.
     * Adds the filter of the filtered task list by the given end time
     */
    void addTaskListFilterByEndTime(Date endTime, boolean negated);

    /**
     * Precondition: arguments are not null.
     * Adds the filter of the filtered task list by between start time and end time
     */
    void addTaskListFilterByStartToEndTime(Date startTime, Date endTime, boolean negated);

    /**
     * Precondition: arguments are not null.
     * Adds the filter of the filtered task list by the given tag names
     */
    void addTaskListFilterByTags(Set<String> tags, boolean negated);

    /**
     * Precondition: arguments are not null.
     * Sorts the filtered task list by the given sorting order
     */
    void sortFilteredTaskListByOrder(String sortOrder);

    /** Updates the filtered task list by the added filters */
    void updateFilteredTaskListByFilter();

    /** Updates the old task with new task details */
    void updateTask(ReadOnlyTask oldTask, Task newTask) throws TaskNotFoundException;

    /**
     * Precondition: numToRedo must be > 0.
     * Redo the past undos.
     * @return number of history states redone
     */
    int redoTaskListHistory(int numToRedo);
}
