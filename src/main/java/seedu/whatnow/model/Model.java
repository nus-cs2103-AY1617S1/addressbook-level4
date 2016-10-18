package seedu.whatnow.model;

import java.io.IOException;
import java.util.Set;
import java.util.Stack;

import seedu.whatnow.commons.core.UnmodifiableObservableList;
import seedu.whatnow.commons.exceptions.DataConversionException;
import seedu.whatnow.logic.commands.Command;
import seedu.whatnow.model.task.ReadOnlyTask;
import seedu.whatnow.model.task.Task;
import seedu.whatnow.model.task.UniqueTaskList;
import seedu.whatnow.model.task.UniqueTaskList.TaskNotFoundException;

/**
 * The API of the Model component.
 */
public interface Model {
    /** Clears existing backing model and replaces with the provided new data. */
    void resetData(ReadOnlyWhatNow newData);

    /** Returns the WhatNow */
    ReadOnlyWhatNow getWhatNow();
    
  //=========== Methods for Task List ===============================================================

    /** Deletes the given task. */
    void deleteTask(ReadOnlyTask target) throws UniqueTaskList.TaskNotFoundException;

    /** Adds the given task */
    void addTask(Task task) throws UniqueTaskList.DuplicateTaskException;

    /** Changes the file data storage location */
    void changeTask(ReadOnlyTask target) throws DataConversionException, IOException, TaskNotFoundException;

    /** Returns the filtered task list as an {@code UnmodifiableObservableList<ReadOnlyTask>} */
    UnmodifiableObservableList<ReadOnlyTask> getCurrentFilteredTaskList();
    
    /** Returns the filtered task list as an {@code UnmodifiableObservableList<ReadOnlyTask>} */
    UnmodifiableObservableList<ReadOnlyTask> getFilteredTaskList();
    
    /** Returns the filtered task list with filter keyword as an {@code UnmodifiableObservableList<ReadOnlyTask>} */
    UnmodifiableObservableList<ReadOnlyTask> getFilteredTaskList(Set<String> key);

    /** Updates the filter of the filtered task list to show all tasks */
    void updateFilteredListToShowAll();

    /** Updates the filter of the filtered task list to filter by the given keywords*/
    void updateFilteredTaskList(Set<String> keywords);
    
    /** Updates the filter of the filtered task list to show only task of a specific status specified by the keyword */
    void updateFilteredListToShowAllByStatus(Set<String> keyword);
    
    /** Update the given task */
    void updateTask(ReadOnlyTask old, Task toUpdate) throws UniqueTaskList.TaskNotFoundException;
    
    /** Mark the given task as completed */
    void markTask(ReadOnlyTask target) throws UniqueTaskList.TaskNotFoundException;
   
    /**Gets the UndoStack if possible */
    Stack<Command> getUndoStack();
    
    /**Gets the redoStack if possible*/
    Stack<Command> getRedoStack();
    
  //=========== Methods for Schedule List ===============================================================
    /** Returns the filtered task list as an {@code UnmodifiableObservableList<ReadOnlyTask>} */
    UnmodifiableObservableList<ReadOnlyTask> getCurrentFilteredScheduleList();
    
    /** Returns the filtered task list as an {@code UnmodifiableObservableList<ReadOnlyTask>} */
    UnmodifiableObservableList<ReadOnlyTask> getFilteredScheduleList();
    
    /** Returns the filtered task list with filter keyword as an {@code UnmodifiableObservableList<ReadOnlyTask>} */
    UnmodifiableObservableList<ReadOnlyTask> getFilteredScheduleList(Set<String> key);
    
    /** Updates the filter of the filtered task list to show all tasks */
    void updateFilteredScheduleListToShowAll();

    /** Updates the filter of the filtered task list to filter by the given keywords*/
    void updateFilteredScheduleList(Set<String> keywords);
    
    /** Updates the filter of the filtered task list to show only task of a specific status specified by the keyword */
    void updateFilteredScheduleListToShowAllByStatus(Set<String> keyword);

}
