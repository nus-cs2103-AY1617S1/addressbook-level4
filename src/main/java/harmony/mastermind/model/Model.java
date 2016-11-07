package harmony.mastermind.model;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.EmptyStackException;
import java.util.Set;

import javafx.collections.ObservableList;

import harmony.mastermind.commons.core.UnmodifiableObservableList;
import harmony.mastermind.commons.exceptions.FolderDoesNotExistException;
import harmony.mastermind.commons.exceptions.NotRecurringTaskException;
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

    //@@author A0124797R
    /** Deletes the given Archived Task */
    void deleteArchive(ReadOnlyTask target) throws TaskNotFoundException, ArchiveTaskList.TaskNotFoundException;

    //@@author
    /** Adds the given task */
    void addTask(Task task) throws UniqueTaskList.DuplicateTaskException;

    //@@author A0124797R
    /** Add the next recurring task */
    void addNextTask(Task task) throws UniqueTaskList.DuplicateTaskException, NotRecurringTaskException;

    //@@author A0124797R
    /** Marks the given task as done */
    void markTask(Task target) throws UniqueTaskList.TaskNotFoundException;
    
    /** Marks the given List of due tasks as done */
    void markDue(ArrayList<Task> targets) throws UniqueTaskList.TaskNotFoundException;

    //@@author A0124797R
    /** Updates the completed task as not done */
    void unmarkTask(Task target) throws UniqueTaskList.DuplicateTaskException,
    ArchiveTaskList.TaskNotFoundException;
    
    //@@author A0139194X
    /** Relocates save location to given directory */
    void relocateSaveLocation(String directory) throws FolderDoesNotExistException;
    
    
    /** push the command to undo history */
    void pushToUndoHistory(Undoable command);

    //@@author A0138862W
    /** undo last action performed, throws EmptyStackException is there's no more action can be undone **/
    CommandResult undo() throws EmptyStackException;
    
    //@@author A0124797R
    /** Returns the current list as an {@code UnmodifiableObservableList<ReadOnlyTask>} */
    UnmodifiableObservableList<ReadOnlyTask> getCurrentList();

    //@@author A0138862W
    /** push the command to redo history */
    void pushToRedoHistory(Redoable command);
    
    //@@author A0138862W
    /** undo last action performed, throws EmptyStackException is there's no more action can be undone **/
    CommandResult redo() throws EmptyStackException;
    
    /** empty redoHistory **/
    // required when a new command is entered, model should throw away all remaining commands in the redo history
    void clearRedoHistory();
    
    /** Returns the filtered task list as an {@code UnmodifiableObservableList<ReadOnlyTask>} */
    //@@author generated
    UnmodifiableObservableList<ReadOnlyTask> getFilteredTaskList();
    
    //@@author A0124797R
    /** Returns the filtered floating task list as an {@code UnmodifiableObservableList<ReadOnlyTask>} */
    UnmodifiableObservableList<ReadOnlyTask> getFilteredFloatingTaskList();
    
    /** Returns the filtered event list as an {@code UnmodifiableObservableList<ReadOnlyTask>} */
    UnmodifiableObservableList<ReadOnlyTask> getFilteredEventList();
    
    /** Returns the filtered deadline list as an {@code UnmodifiableObservableList<ReadOnlyTask>} */
    UnmodifiableObservableList<ReadOnlyTask> getFilteredDeadlineList();
    
    /** Returns the filtered archive list as an {@code UnmodifiableObservableList<ReadOnlyTask>} */
    UnmodifiableObservableList<ReadOnlyTask> getFilteredArchiveList();

    //@@author A0138862W
    /** Returns filtered task list as an {@code ObervableList<Task>} */
    ObservableList<Task> getListToMark();

    //@@author A0124797R
    /** update current tab to the specified tab*/
    void updateCurrentTab(String tab);

    /** Updates the filter of the filtered task list for current tab to show all tasks */
    void updateFilteredListToShowAll();
    
    /** Updates the filter of the filtered task list for specified tab to show all tasks */
    void updateFilteredListToShow(String tab);
    
    /** Updates the filter of the filtered task list 
     * for Home tab to show all upcoming tasks */
    void updateFilteredListToShowUpcoming(long time, String taskType);
    
    //@@author
    /** Updates the filter of the filtered task list to filter by the given keywords*/
    void updateFilteredList(Set<String> keywords);

    /** Updates the filter of the filtered task list to filter by the given tag keywords*/
    void updateFilteredTagTaskList(Set<Tag> keywords);
    
    //@@author A0124797R
    String getCurrentTab();
    
    /** get the filtered list size for the current tab */
    int getCurrentListSize();

    //@@author A0138862W
    /** Search */
    void searchTask(String input);

    //@@author A0124797R
    /** reads the file indicated */
    BufferedReader importFile(String fileToImport) throws FileNotFoundException;


}
