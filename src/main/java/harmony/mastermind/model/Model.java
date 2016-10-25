package harmony.mastermind.model;

import java.util.EmptyStackException;
import java.util.Set;

import javafx.collections.ObservableList;

import harmony.mastermind.commons.core.UnmodifiableObservableList;
import harmony.mastermind.commons.exceptions.FolderDoesNotExistException;
import harmony.mastermind.commons.exceptions.NotRecurringTaskException;
import harmony.mastermind.commons.exceptions.CommandCancelledException;
import harmony.mastermind.logic.commands.CommandResult;
import harmony.mastermind.logic.commands.Redoable;
import harmony.mastermind.logic.commands.Undoable;
import harmony.mastermind.model.tag.Tag;
import harmony.mastermind.model.task.ArchiveTaskList;
import harmony.mastermind.model.task.ReadOnlyTask;
import harmony.mastermind.model.task.Task;
import harmony.mastermind.model.task.UniqueTaskList;
import harmony.mastermind.model.task.UniqueTaskList.TaskNotFoundException;

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
    
    /** Deletes the given Archived Task */
    //@@author A0124797R
    void deleteArchive(ReadOnlyTask target) throws TaskNotFoundException, ArchiveTaskList.TaskNotFoundException;

    /** Adds the given task */
    void addTask(Task task) throws UniqueTaskList.DuplicateTaskException;
    
    /** Add the next recurring task */
    //@@author A0124797R
    void addNextTask(Task task) throws UniqueTaskList.DuplicateTaskException, NotRecurringTaskException;

    /** Marks the given task as done */
    //@@author A0124797R
    void markTask(Task target) throws UniqueTaskList.TaskNotFoundException;
    
    /** Updates the completed task as not done */
    //@@author A0124797R
    void unmarkTask(Task target) throws UniqueTaskList.DuplicateTaskException,
    ArchiveTaskList.TaskNotFoundException;
    
    //@@author A0139194X
    /** Relocates save location to given directory */
    void relocateSaveLocation(String directory) throws FolderDoesNotExistException;
    
    
    /** push the command to undo history */
    void pushToUndoHistory(Undoable command);
    
    /** undo last action performed, throws EmptyStackException is there's no more action can be undone **/
    CommandResult undo() throws EmptyStackException;
    
    //@@author A0124797R
    /** Returns the current list as an {@code UnmodifiableObservableList<ReadOnlyTask>} */
    UnmodifiableObservableList<ReadOnlyTask> getCurrentList();

    /** push the command to redo history */
    void pushToRedoHistory(Redoable command);
    
    /** undo last action performed, throws EmptyStackException is there's no more action can be undone **/
    CommandResult redo() throws EmptyStackException;
    
    /** empty redoHistory **/
    // required when a new command is entered, model should throw away all remaining commands in the redo history
    void clearRedoHistory();
    
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

    //@@author A0124797R
    /** update current tab */
    void updateCurrentTab(String tab);

    /** Updates the filter of the filtered task list for current tab to show all tasks */
    void updateFilteredListToShowAll();
    
    //@@author A0124797R
    /** Updates the filter of the filtered task list 
     * for Home tab to show all upcoming tasks */
    void updateFilteredListToShowUpcoming(long time);
    
    /** Updates the filter of the filtered task list to filter by the given keywords*/
    void updateFilteredTaskList(Set<String> keywords);

    /** Updates the filter of the filtered task list to filter by the given keywords*/
    void updateFilteredTagTaskList(Set<Tag> keywords);
    
    /** Search */
    void searchTask(String input);

    //@@author A0139194X
    /** Indicate that user needs to confirm command execution */
    void indicateConfirmationToUser() throws CommandCancelledException;


}
