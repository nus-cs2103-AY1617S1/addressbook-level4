package seedu.address.model;

import seedu.address.commons.core.UnmodifiableObservableList;
import seedu.address.commons.events.ui.AgendaTimeRangeChangedEvent;
import seedu.address.model.task.Task;
import seedu.address.model.task.TaskOccurrence;
import seedu.address.model.task.TaskDate;
import seedu.address.model.ModelManager.Expression;
import seedu.address.model.tag.UniqueTagList;
import seedu.address.model.task.Name;
import seedu.address.model.task.ReadOnlyTask;
import seedu.address.model.task.RecurringType;
import seedu.address.model.task.UniqueTaskList;
import seedu.address.model.task.UniqueTaskList.TaskNotFoundException;
import seedu.address.model.task.UniqueTaskList.TimeslotOverlapException;

import java.util.Date;
import java.util.List;
import java.util.Set;

/**
 * The API of the Model component.
 */
public interface Model {
    /** Clears existing backing model and replaces with the provided new data. */
    void resetData(ReadOnlyTaskMaster newData);

    /** Returns the TaskMaster */
    ReadOnlyTaskMaster getTaskMaster();

    /** Deletes the given task. */
    void deleteTask(TaskOccurrence target) throws UniqueTaskList.TaskNotFoundException;

    /** Adds the given task 
     * @throws TimeslotOverlapException */
    void addTask(Task task) throws UniqueTaskList.DuplicateTaskException, TimeslotOverlapException;
    
    /** Returns the list of tasks stored in the model.*/
    List<ReadOnlyTask> getTaskList();
    
    /** Returns the filtered task list as an {@code UnmodifiableObservableList<ReadOnlyTask>} */
    UnmodifiableObservableList<TaskOccurrence> getFilteredTaskComponentList();
    
    /** Updates the filter of the filtered task list to show all tasks */
    void updateFilteredListToShowAll();

    /** Updates the filter of the filtered task list to filter by the given keywords*/
    void updateFilteredTaskList(Set<String> keywords, Set<String> tags, Date startDate, Date endDate, Date deadline);
	
	/** Edits the given task.*/
	void editTask(Task target, Name name, UniqueTagList tags, TaskDate startDate, TaskDate endDate, RecurringType recurringType) throws TaskNotFoundException, TimeslotOverlapException;
	
	//@@author A0147967J
	/** Returns previous filtering expression. */
	Expression getPreviousExpression();
	
	/** Updates(Resets) the filtered list with given expression.*/
    void updateFilteredTaskList(Expression expression);
    
    /** Updates the file path for current storage manager of the model.*/
    void changeDirectory(String filePath);
    
    /** Archives the given task component. */
    void archiveTask(TaskOccurrence target) throws TaskNotFoundException;
    
    /** Returns current system time.*/
    TaskDate getPreviousDate();
    
    /** Sets the system time. */
    void setSystemTime(AgendaTimeRangeChangedEvent atrce);
}
