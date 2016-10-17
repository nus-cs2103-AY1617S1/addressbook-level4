package seedu.task.model;

import seedu.task.model.item.Event;
import seedu.task.model.item.ReadOnlyEvent;
import seedu.task.model.item.ReadOnlyTask;
import seedu.task.model.item.Task;
import seedu.task.model.item.UniqueEventList;
import seedu.task.model.item.UniqueTaskList;
import seedu.taskcommons.core.UnmodifiableObservableList;

import java.util.Set;

/**
 * The API of the Model component.
 */
public interface Model {
    /** Clears existing backing model and replaces with the provided new data. */
    void resetData(ReadOnlyTaskBook newData);

    /** Returns the TaskBook */
    ReadOnlyTaskBook getTaskBook();

    /** Deletes the given task. */
    void deleteTask(ReadOnlyTask target) throws UniqueTaskList.TaskNotFoundException;

    /** Deletes the given event. */
    void deleteEvent(ReadOnlyEvent target) throws UniqueEventList.EventNotFoundException;

    /** Adds the given task */
    void addTask(Task task) throws UniqueTaskList.DuplicateTaskException;
    
    /** Adds the given event */
    void addEvent(Event event) throws UniqueEventList.DuplicateEventException;
    
    /** Edits the given task */
    void editTask(Task task, int index) throws UniqueTaskList.DuplicateTaskException;
    
    /** Marks the given task */
    void markTask(ReadOnlyTask target);

    /** Returns the filtered task list as an {@code UnmodifiableObservableList<ReadOnlyTask>} */
    UnmodifiableObservableList<ReadOnlyTask> getFilteredTaskList();
    
    /** Returns the filtered event list as an {@code UnmodifiableObservableList<ReadOnlyEvent>} */
    UnmodifiableObservableList<ReadOnlyEvent> getFilteredEventList();

    /** Updates the filter of the filtered task list to show all tasks */
    void updateFilteredTaskListToShowAll();

    /** Updates the filter of the filtered task list to filter by the given keywords*/
    void updateFilteredTaskList(Set<String> keywords);
    
    /** Updates the filter of the filtered event list to filter by the given keywords*/
    void updateFilteredEventList(Set<String> keywords);
    
    /** Updates the filter of the filtered task list to filter by the status*/
    void updateFilteredTaskListToShowWithStatus(Boolean statusCompleted);

    /** Updates the filter of the filtered event list to filter by the status*/
	void updateFilteredEventListToShowWithStatus(Boolean statusPassed);
	
	/** Updates the filter of the filtered event list to show all events*/
	void updateFilteredEventListToShowAll();

}
