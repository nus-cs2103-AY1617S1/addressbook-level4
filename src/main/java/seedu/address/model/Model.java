package seedu.address.model;

import seedu.address.commons.core.UnmodifiableObservableList;
import seedu.address.model.item.Task;
import seedu.address.model.item.Name;
import seedu.address.model.item.Priority;
import seedu.address.model.item.ReadOnlyTask;
import seedu.address.model.item.RecurrenceRate;
import seedu.address.model.item.UniqueTaskList;
import seedu.address.model.item.UniqueTaskList.DuplicateTaskException;

import java.util.Date;
import java.util.Set;

/**
 * The API of the Model component.
 */
public interface Model {
    /** Clears existing backing model and replaces with the provided new data. */
    void resetData(ReadOnlyTaskManager newData);

    /** Returns the TaskManager */
    ReadOnlyTaskManager getTaskManager();

    /** Deletes the given floating task. */
    void deleteTask(ReadOnlyTask target) throws UniqueTaskList.TaskNotFoundException;

    /** Adds the given floating task */
    void addTask(Task task);

    /** Returns the filtered floating task list as an {@code UnmodifiableObservableList<ReadOnlyPerson>} */
    UnmodifiableObservableList<ReadOnlyTask> getFilteredFloatingTaskList();

    /** Updates the filter of the filtered floating task list to show all floating tasks */
    void updateFilteredListToShowAll();

    /** Updates the filter of the filtered floating task list to filter by the given keywords*/
    void updateFilteredFloatingTaskList(Set<String> keywords);

    /** Edits the name of the given floating task. */
	void editName(ReadOnlyTask personToEdit, Name taskName) throws DuplicateTaskException;
    
	/** Edits the start date of the given floating task. */
	void editStartDate(ReadOnlyTask personToEdit, Date startDate);

	/** Edits the end date of the given floating task. */
	void editEndDate(ReadOnlyTask personToEdit, Date endDate);
	
    /** Edits the priority of the given floating task. */
	void editPriority(ReadOnlyTask personToEdit, Priority priority);

    


}
