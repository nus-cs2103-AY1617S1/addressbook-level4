package seedu.emeraldo.model;

import seedu.emeraldo.commons.core.UnmodifiableObservableList;
import seedu.emeraldo.model.task.DateTime;
import seedu.emeraldo.model.task.Description;
import seedu.emeraldo.model.task.ReadOnlyTask;
import seedu.emeraldo.model.task.Task;
import seedu.emeraldo.model.task.UniqueTaskList.TaskNotFoundException;
import seedu.emeraldo.model.task.UniqueTaskList;

import java.util.EmptyStackException;
import java.util.Set;

/**
 * The API of the Model component.
 */
public interface Model {
	
	//@@author A0139196U
	/** Undo the previous changes made to the model 
	 * @throws UndoException */
	void undoChanges() throws EmptyStackException, UndoException;
	//@@author
	
	/** Clears existing backing model and replaces with empty data */
	void clearEmeraldo();
	
    /** Clears existing backing model and replaces with the provided new data. */
    void resetData(ReadOnlyEmeraldo newData);

    /** Returns Emeraldo */
    ReadOnlyEmeraldo getEmeraldo();

    /** Deletes the given task. */
    void deleteTask(ReadOnlyTask target) throws UniqueTaskList.TaskNotFoundException;
    
    //@@author A0139342H
    /** Edits given task */
    void editTask(Task target, Description description, DateTime dateTime) throws TaskNotFoundException;

    //@@author A0142290N
    /** Marks given task as complete */
    void completedTask(Task target) throws TaskNotFoundException;
    //@@author
    
    /** Adds the given task */
    void addTask(Task task) throws UniqueTaskList.DuplicateTaskException;

    /** Returns the filtered task list as an {@code UnmodifiableObservableList<ReadOnlyTask>} */
    UnmodifiableObservableList<ReadOnlyTask> getFilteredTaskList();

    /** Updates the filter of the filtered task list to show all tasks */
    void updateFilteredListToShowAll();

    
    /** Updates the filter of the filtered task list to filter by the given keywords*/
    void updateFilteredTaskList(Set<String> keywords);
    
    /** Updates the filter of the filtered task list to filter by a single given keyword*/
    //@@author A0139749L
    void updateFilteredTaskList(String keyword);

    /** Updates the filter of the filtered task list to filter out tasks with completed tag*/
    void updateFilteredListToShowUncompleted(); 
}
