package seedu.whatnow.model;

import java.io.IOException;
import java.nio.file.Path;
import java.util.Set;
import java.util.Stack;

import seedu.whatnow.commons.core.Config;
import seedu.whatnow.commons.core.UnmodifiableObservableList;
import seedu.whatnow.commons.exceptions.DataConversionException;
import seedu.whatnow.logic.commands.Command;
import seedu.whatnow.model.task.ReadOnlyTask;
import seedu.whatnow.model.task.Task;
import seedu.whatnow.model.task.UniqueTaskList.DuplicateTaskException;
import seedu.whatnow.model.task.UniqueTaskList.TaskNotFoundException;

/**
 * The API of the Model component.
 */
public interface Model {
    /** Clears existing backing model and replaces with the provided new data. */
    void resetData(ReadOnlyWhatNow newData);

    /** Reverts to the pre-existing backing model and replaces with backup-ed data */
	void revertData();
    
	/** Returns the WhatNow */
    ReadOnlyWhatNow getWhatNow();
    
    /** Reverts to previous data of WhatNow */
	void revertDataUpdate();
	
	void revertToPrevDataUpdate();
  //=========== Methods for Task List ===============================================================

    /** Deletes the given task. */
    void deleteTask(ReadOnlyTask target) throws TaskNotFoundException;

    /** Adds the given task */
    void addTask(Task task) throws DuplicateTaskException;

    /** Changes the file data storage location */
    void changeLocation(ReadOnlyTask target) throws DataConversionException, IOException, TaskNotFoundException;

    /** Returns the filtered task list as an {@code UnmodifiableObservableList<ReadOnlyTask>} */
    UnmodifiableObservableList<ReadOnlyTask> getCurrentFilteredTaskList();
    
    /** Returns the filtered task list as an {@code UnmodifiableObservableList<ReadOnlyTask>} */
    UnmodifiableObservableList<ReadOnlyTask> getFilteredTaskList();
    
    /** Returns the filtered task list with filter keyword as an {@code UnmodifiableObservableList<ReadOnlyTask>} */
    UnmodifiableObservableList<ReadOnlyTask> getFilteredTaskList(Set<String> key);

    /**	Returns old copy of the filteredTaskList before a clear */
	UnmodifiableObservableList<ReadOnlyTask> getBackUpFilteredTaskList();
	
    /** Updates the filter of the filtered task list to show all tasks */
    void updateFilteredListToShowAll();
    
    /** Updates the filter of the filtered task list to show all ongoing tasks */
    void updateFilteredListToShowAllIncomplete();
    
    /** Updates the filter of the filtered task list to show all completed tasks */
    void updateFilteredListToShowAllCompleted();

    /** Updates the filter of the filtered task list to filter by the given keywords*/
    void updateFilteredTaskList(Set<String> keywords);
    
    /** Updates the filter of the filtered task list to show only task of a specific status specified by the keyword */
    void updateFilteredListToShowAllByStatus(Set<String> keyword);
    
    /** Update the given task */
    void updateTask(ReadOnlyTask old, Task toUpdate) throws TaskNotFoundException, DuplicateTaskException;
    
    /** Undo the update done on given task */
	void undoUpdateTask(ReadOnlyTask toUpdate, Task old) throws TaskNotFoundException, DuplicateTaskException;

    /** Mark the given task as completed */
    void markTask(ReadOnlyTask target) throws TaskNotFoundException;
   
    /** Mark the given task as incomplete */
	void unMarkTask(ReadOnlyTask target) throws TaskNotFoundException;
	
    /**Gets the UndoStack if possible */
    Stack<Command> getUndoStack();
    
    /**Gets the redoStack if possible*/
    Stack<Command> getRedoStack();
    
    /**Gets the oldTask if possible */
	Stack<ReadOnlyTask> getOldTask();
	
	/**Gets the newTask if possible */
	Stack<ReadOnlyTask> getNewTask();
	
	/** Gets the deletedStackOfTask */
	Stack<ReadOnlyTask> getDeletedStackOfTask();

	/** Gets the deletedStackOfTaskType corresponding to stackOfTask */
	Stack<String> getDeletedStackOfTaskType();
	
	/** Gets Stack of Task that were marked */
	Stack<ReadOnlyTask> getStackOfMarkDoneTask();  
	
	/** Gets stack of TaskTypes corresponding to stackOfMarkDoneTask */
	Stack<String> getStackOfMarkDoneTaskTaskType();
	
	/** Gets a stack of WhatNow corresponding to Undoes of Update */
	Stack<ReadOnlyWhatNow> getStackOfWhatNowUpdate();  
	
	/** Gets a stack of WhatNow corresponding to Redoes of Update */
	Stack<ReadOnlyWhatNow> getStackOfWhatNowRedoUpdate();
    
  //=========== Methods for Schedule List ===============================================================
    /** Returns the filtered task list as an {@code UnmodifiableObservableList<ReadOnlyTask>} */
    UnmodifiableObservableList<ReadOnlyTask> getCurrentFilteredScheduleList();
    
    /** Returns the filtered task list as an {@code UnmodifiableObservableList<ReadOnlyTask>} */
    UnmodifiableObservableList<ReadOnlyTask> getFilteredScheduleList();
    
    /** Returns the filtered task list with filter keyword as an {@code UnmodifiableObservableList<ReadOnlyTask>} */
    UnmodifiableObservableList<ReadOnlyTask> getFilteredScheduleList(Set<String> key);
    
    /** Returns the filtered task list before the clear */
	UnmodifiableObservableList<ReadOnlyTask> getBackUpFilteredScheduleList();
    
    /** Updates the filter of the filtered task list to show all tasks */
    void updateFilteredScheduleListToShowAll();
    
    /** Updates the filter of the filtered task list to show all ongoing tasks */
    void updateFilteredScheduleListToShowAllIncomplete();
    
    /** Updates the filter of the filtered task list to show all completed tasks */
    void updateFilteredScheduleListToShowAllCompleted();

    /** Updates the filter of the filtered task list to filter by the given keywords*/
    void updateFilteredScheduleList(Set<String> keywords);
    
    /** Updates the filter of the filtered task list to show only task of a specific status specified by the keyword */
    void updateFilteredScheduleListToShowAllByStatus(Set<String> keyword);

    /** Updates the filter of the filtered task list to display all task types*/
    UnmodifiableObservableList<ReadOnlyTask> getAllTaskTypeList();

    void changeLocation(Path destination, Config config) throws DataConversionException, IOException, TaskNotFoundException;
}
