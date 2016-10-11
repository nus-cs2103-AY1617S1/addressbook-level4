package seedu.agendum.model;

import seedu.agendum.commons.core.UnmodifiableObservableList;
import seedu.agendum.model.task.Name;
import seedu.agendum.model.task.ReadOnlyTask;
import seedu.agendum.model.task.Task;
import seedu.agendum.model.task.UniqueTaskList;
import seedu.agendum.model.task.UniqueTaskList.TaskNotFoundException;

import java.time.LocalDateTime;
import java.util.Optional;
import java.util.Set;

/**
 * The API of the Model component.
 */
public interface Model {
    /** Clears existing backing model and replaces with the provided new data. */
    void resetData(ReadOnlyToDoList newData);

    /** Returns the ToDoList */
    ReadOnlyToDoList getToDoList();

    /** Deletes the given task. */
    void deleteTask(ReadOnlyTask target) throws UniqueTaskList.TaskNotFoundException;

    /** Adds the given task */
    void addTask(Task task) throws UniqueTaskList.DuplicateTaskException;
    
    /** Rename the given task */
    void renameTask(ReadOnlyTask target, Name newTaskName)
            throws UniqueTaskList.TaskNotFoundException, UniqueTaskList.DuplicateTaskException;
    
    /**Schedules the given task */
    void scheduleTask(ReadOnlyTask target, Optional<LocalDateTime> startDateTime,
            Optional<LocalDateTime> endDateTime) throws UniqueTaskList.TaskNotFoundException;
       
    /** Marks the given task as completed */
    void markTask(ReadOnlyTask target) throws UniqueTaskList.TaskNotFoundException;
    
    /** Unmarks the given task */
    void unmarkTask(ReadOnlyTask target) throws UniqueTaskList.TaskNotFoundException;
    
    /** Returns the filtered task list as an {@code UnmodifiableObservableList<ReadOnlyTask>} */
    UnmodifiableObservableList<ReadOnlyTask> getFilteredTaskList();

    /** Updates the filter of the filtered task list to show all tasks */
    void updateFilteredListToShowAll();

    /** Updates the filter of the filtered task list to filter by the given keywords*/
    void updateFilteredTaskList(Set<String> keywords);

}
