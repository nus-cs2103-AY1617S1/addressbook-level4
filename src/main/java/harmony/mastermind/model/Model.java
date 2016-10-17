package harmony.mastermind.model;

import java.util.EmptyStackException;
import java.util.Set;
import java.util.Stack;

import harmony.mastermind.commons.core.UnmodifiableObservableList;
import harmony.mastermind.logic.commands.CommandResult;
import harmony.mastermind.logic.commands.Undoable;
import harmony.mastermind.model.tag.Tag;
import harmony.mastermind.model.task.ArchiveTaskList;
import harmony.mastermind.model.task.ReadOnlyTask;
import harmony.mastermind.model.task.Task;
import harmony.mastermind.model.task.UniqueTaskList;
import harmony.mastermind.model.task.UniqueTaskList.DuplicateTaskException;
import harmony.mastermind.model.task.UniqueTaskList.TaskNotFoundException;
import javafx.collections.ObservableList;

/**
 * The API of the Model component.
 */
public interface Model {
    /** Clears existing backing model and replaces with the provided new data. */
    void resetData(ReadOnlyTaskManager newData);

    /** Returns the TaskManager*/
    ReadOnlyTaskManager getTaskManager();

    /** Deletes the given task. */
    void deleteTask(ReadOnlyTask target) throws UniqueTaskList.TaskNotFoundException;

    /** Adds the given task */
    void addTask(Task task) throws UniqueTaskList.DuplicateTaskException;

    /** Marks the given task as done */
    //@@author A0124797R
    void markTask(Task target) throws UniqueTaskList.TaskNotFoundException, DuplicateTaskException;
    
    /** Updates the completed task as not done */
    //@@author A0124797R
    void unmarkTask(Task target) throws UniqueTaskList.DuplicateTaskException,
    ArchiveTaskList.TaskNotFoundException;
    
    /** Relocates save location to given directory */
    void relocateSaveLocation(String directory);
    
    /** push the command to undo history */
    void pushToUndoHistory(Undoable command);
    
    /** undo last action performed, throws EmptyStackException is there's no more action can be undone **/
    CommandResult undo() throws EmptyStackException;

    /** Returns the filtered task list as an {@code UnmodifiableObservableList<ReadOnlyTask>} */
    UnmodifiableObservableList<ReadOnlyTask> getFilteredTaskList();
    
    //@@author A0124797R
    /** Returns the filtered floating task list as an {@code UnmodifiableObservableList<ReadOnlyTask>} */
    UnmodifiableObservableList<ReadOnlyTask> getFilteredFloatingTaskList();
    
    //@@author A0124797R
    /** Returns the filtered event list as an {@code UnmodifiableObservableList<ReadOnlyTask>} */
    UnmodifiableObservableList<ReadOnlyTask> getFilteredEventList();
    
    //@@author A0124797R
    /** Returns the filtered deadline list as an {@code UnmodifiableObservableList<ReadOnlyTask>} */
    UnmodifiableObservableList<ReadOnlyTask> getFilteredDeadlineList();
    
    //@@author A0124797R
    /** Returns the filtered archive list as an {@code UnmodifiableObservableList<ReadOnlyTask>} */
    UnmodifiableObservableList<ReadOnlyTask> getFilteredArchiveList();

    //@@author A0124797R
    /** Returns filtered task list as an {@code ObervableList<Task>} */
    ObservableList<Task> getListToMark();

    /** Updates the filter of the filtered task list to show all tasks */
    void updateFilteredListToShowAll();
    
    /** Updates the filter of the filtered task list to filter by the given keywords*/
    void updateFilteredTaskList(Set<String> keywords);

    /** Updates the filter of the filtered task list to filter by the given keywords*/
    void updateFilteredTagTaskList(Set<Tag> keywords);
    
    /** Search */
    void searchTask(String input);


}
