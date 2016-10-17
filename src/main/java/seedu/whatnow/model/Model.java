package seedu.whatnow.model;

import java.util.Set;

import seedu.whatnow.commons.core.UnmodifiableObservableList;
import seedu.whatnow.model.task.ReadOnlyTask;
import seedu.whatnow.model.task.Task;
import seedu.whatnow.model.task.UniqueTaskList;

/**
 * The API of the Model component.
 */
public interface Model {
    /** Clears existing backing model and replaces with the provided new data. */
    void resetData(ReadOnlyWhatNow newData);

    /** Returns the WhatNow */
    ReadOnlyWhatNow getWhatNow();

    /** Deletes the given task. */
    void deleteTask(ReadOnlyTask target) throws UniqueTaskList.TaskNotFoundException;

    /** Adds the given task */
    void addTask(Task task) throws UniqueTaskList.DuplicateTaskException;

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
    
    /** Returns the filtered task list as an {@code UnmodifiableObservableList<ReadOnlyTask>} */
    UnmodifiableObservableList<ReadOnlyTask> getFilteredScheduleList();
    
    /** Updates the filter of the filtered task list to show all tasks */
    void updateFilteredScheduleListToShowAll();

    /** Updates the filter of the filtered task list to filter by the given keywords*/
    void updateFilteredScheduleList(Set<String> keywords);
    
    /** Updates the filter of the filtered task list to show only task of a specific status specified by the keyword */
    void updateFilteredScheduleListToShowAllByStatus(Set<String> keyword);
    
    
}
