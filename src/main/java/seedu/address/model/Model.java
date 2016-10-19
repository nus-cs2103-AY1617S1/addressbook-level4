package seedu.address.model;

import seedu.address.commons.core.UnmodifiableObservableList;
import seedu.address.model.item.Task;
import seedu.address.model.item.Name;
import seedu.address.model.item.Priority;
import seedu.address.model.item.ReadOnlyTask;
import seedu.address.model.item.RecurrenceRate;
import seedu.address.model.item.UniqueTaskList;
import seedu.address.model.item.UniqueTaskList.DuplicateTaskException;
import seedu.address.model.item.UniqueTaskList.TaskNotFoundException;

import java.util.Date;
import java.util.List;
import java.util.Set;

/**
 * The API of the Model component.
 */
public interface Model {
    /** Clears existing backing model and replaces with the provided new data. */
    void resetData(ReadOnlyTaskManager newData);

    /** Returns the TaskManager */
    ReadOnlyTaskManager getTaskManager();

    /** Adds the given floating task */
    void addTask(Task task);
    
    /** Deletes the given floating task. */
    void deleteTask(ReadOnlyTask target) throws UniqueTaskList.TaskNotFoundException;

    /** Add the given floating tasks */
    void addTasks(List<Task> tasks);
    
    /** Delete the given floating tasks. */
    void deleteTasks(List<ReadOnlyTask> targets);
       
    /** Archives the task by adding it into DoneTaskList */
    void addDoneTask(Task task);

    /** Removes the task permanently from the archive DoneTaskList **/
    void deleteDoneTask(ReadOnlyTask floatingTask) throws TaskNotFoundException;
    
    /** Archives the tasks by adding them into DoneTaskList */
    void addDoneTasks(List<Task> task);

    /** Removes the tasks permanently from the archive DoneTaskList **/
    void deleteDoneTasks(List<ReadOnlyTask> floatingTask);
    
    /** Returns the filtered undone task list as an {@code UnmodifiableObservableList<ReadOnlyPerson>} */
    UnmodifiableObservableList<ReadOnlyTask> getFilteredUndoneTaskList();
    
    /** Returns the filtered done task list as an {@code UnmodifiableObservableList<ReadOnlyPerson>} */
    UnmodifiableObservableList<ReadOnlyTask> getFilteredDoneTaskList();

    /** Updates the filter of the filtered floating task list to show all floating tasks */
    void updateFilteredListToShowAll();

    /** Updates the filter of the filtered floating task list to filter by the given keywords*/
    void updateFilteredFloatingTaskList(Set<String> keywords);

    /** Edits the name of the given floating task. */
	void editName(ReadOnlyTask personToEdit, Name taskName);
    
	/** Edits the start date of the given floating task. */
	void editStartDate(ReadOnlyTask personToEdit, Date startDate);

	/** Edits the end date of the given floating task. */
	void editEndDate(ReadOnlyTask personToEdit, Date endDate);
	
    /** Edits the priority of the given floating task. */
	void editPriority(ReadOnlyTask personToEdit, Priority priority);

    /** Edits the recurrence of the given floating task. */
	void editRecurrence(ReadOnlyTask personToEdit, RecurrenceRate recurrenceRate);

	void editTask(ReadOnlyTask taskToEdit, Name taskName, Date startDate, Date endDate, Priority priority,
			RecurrenceRate recurrenceRate);





}
