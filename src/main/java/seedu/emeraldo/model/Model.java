package seedu.emeraldo.model;

import seedu.emeraldo.commons.core.UnmodifiableObservableList;
import seedu.emeraldo.commons.exceptions.TagListEmptyException;
import seedu.emeraldo.commons.exceptions.TaskAlreadyCompletedException;
import seedu.emeraldo.logic.commands.ListCommand.Completed;
import seedu.emeraldo.logic.commands.ListCommand.TimePeriod;
import seedu.emeraldo.model.tag.Tag;
import seedu.emeraldo.model.tag.UniqueTagList;
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
    /** Marks given task as complete 
     * @throws TaskAlreadyCompletedException */
    void completedTask(Task target) throws TaskAlreadyCompletedException;
    //@@author
    
    /** Adds the given task */
    void addTask(Task task) throws UniqueTaskList.DuplicateTaskException;
    
    /** Adds the tag to the specified task */
    void addTag(Task taskTagToEdit, Tag tag);
    
    /** Deletes the tag from the specified task */
    void deleteTag(Task taskTagToEdit, Tag tag);

    /** Clears all tags from the specified task */
    void clearTag(Task taskTagToEdit) throws TagListEmptyException;

    /** Returns the filtered task list as an {@code UnmodifiableObservableList<ReadOnlyTask>} */
    UnmodifiableObservableList<ReadOnlyTask> getFilteredTaskList();

    /** Updates the filter of the filtered task list to show all tasks */
    void updateFilteredListToShowAll();

//=========== Filtered Task List that are completed ==============================================
    
    //@@author A0139749L
    /** Updates the filter of the filtered task list to filter by the given keywords
     * without task with completed tag*/
    void updateFilteredTaskList(Set<String> keywords);
    
    /** Updates the filter of the filtered task list to filter by a single given keyword
     * without task with completed tag*/
    void updateFilteredTaskList(String keyword);
    
    /** Updates the filter of the filtered task list to filter by the given time period
     * without task with completed tag*/
    void updateFilteredTaskList(TimePeriod keywords);
    
    /** Updates the filter of the filtered task list to show all tasks without completed tag*/
    void updateFilteredListToShowUncompleted();
    
//=========== Filtered Task List that including completed ones ==============================================
    
    /** Updates the filter of the filtered task list to filter by the given keywords*/
	void updateFilteredTaskListWithCompleted(Set<String> keywords);

    /** Updates the filter of the filtered task list to filter by a single given keyword*/
	void updateFilteredTaskListWithCompleted(String keyword); 
    
    /** Updates the filter of the filtered task list to filter by the given time period*/
    void updateFilteredTaskListWithCompleted(TimePeriod keyword);
    
    /**Updates the filter of the filtered task list to filter by the keyword "completed*/
    void updateFilteredTaskList(Completed keyword);

}
