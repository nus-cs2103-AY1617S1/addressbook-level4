package seedu.dailyplanner.model;

import java.util.Set;

import javafx.beans.property.IntegerProperty;
import javafx.beans.property.StringProperty;
import javafx.collections.ObservableList;
import seedu.dailyplanner.commons.core.UnmodifiableObservableList;
import seedu.dailyplanner.logic.commands.Command;
import seedu.dailyplanner.history.HistoryManager;
import seedu.dailyplanner.model.task.ReadOnlyTask;
import seedu.dailyplanner.model.task.Task;
import seedu.dailyplanner.model.task.UniqueTaskList;
import seedu.dailyplanner.model.task.UniqueTaskList.TaskNotFoundException;

/**
 * The API of the Model component.
 */
public interface Model {
    
	
	/** Clears existing backing model and replaces with the provided new data. */
    void resetData(ReadOnlyDailyPlanner newData);

    /** Returns the DailyPlanner */
    ReadOnlyDailyPlanner getDailyPlanner();
    
    HistoryManager getHistory();

    /** Deletes the given task. */
    void deleteTask(ReadOnlyTask target) throws UniqueTaskList.TaskNotFoundException;

    /** Adds the given task */
    void addTask(Task task) throws UniqueTaskList.DuplicateTaskException;
  //@@author A0139102U
    /** Marks the given task as complete  */
    void markTaskAsComplete(ReadOnlyTask taskToComplete) throws TaskNotFoundException;
    
    /** Unmarks the given task as incomplete  */
    void markTaskAsIncomplete(ReadOnlyTask taskToIncomplete) throws TaskNotFoundException;
    
    /** Pins the given task. */
    void pinTask(ReadOnlyTask taskToPin) throws TaskNotFoundException;

    /** Unpins the given task. */
	void unpinTask(int i) throws TaskNotFoundException;
    
    /** Returns the filtered task list as an {@code UnmodifiableObservableList<ReadOnlyTask>} */
    UnmodifiableObservableList<ReadOnlyTask> getFilteredTaskList();
    
    /** Returns the list of pinned task as an {@code UnmodifiableObservableList<ReadOnlyTask>} */
    UnmodifiableObservableList<ReadOnlyTask> getPinnedTaskList();

    /** Updates the filter of the filtered task list to show all tasks */
    void updateFilteredListToShowAll();

    /** Updates the filter of the filtered task list to filter by the given keywords*/
    void updateFilteredTaskList(Set<String> keywords);
    
    /** Updates the filter of the filtered task list to filter by the given date*/
    void updateFilteredTaskListByDate(Set<String> keywords);

    /** Updates the filter of the filtered task list to show only completed tasks*/
	void updateFilteredTaskListByCompletion(Set<String> keywords);
	
    /** Returns the index of the last task that was added to the task list */
    public int getLastTaskAddedIndex();
    
    /** Sets the stored index of the last task added */
    public void setLastTaskAddedIndex(int index);
  
    /** Returns the last task added index as the property itself */
    public IntegerProperty getLastTaskAddedIndexProperty();
    
    /** Returns last shown date command */
    public String getLastShowDate();
    
    /** Sets last shown date given by show command, this date is dislayed in green beside 'Your Tasks' in GUI*/
    public void setLastShowDate(String showInput);
    
    /** Returns the StringProperty holding the last shown date command */
    public StringProperty getLastShowDateProperty();
 
    /** Resets the pinboard to an empty pinboard */
	public void resetPinBoard();
	
	/** Uncompletes task with given index in taskList */
    public void uncompleteTask(int indexInTaskList);
	
	/** Refreshes the pin board after a command is carried out */
	public void updatePinBoard();

}
