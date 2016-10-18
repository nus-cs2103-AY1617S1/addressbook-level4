package tars.model;

import tars.commons.core.UnmodifiableObservableList;
import tars.commons.exceptions.DuplicateTaskException;
import tars.commons.exceptions.IllegalValueException;
import tars.commons.flags.Flag;
import tars.logic.commands.Command;
import tars.model.task.Task;
import tars.model.task.TaskQuery;
import tars.model.tag.UniqueTagList.TagNotFoundException;
import tars.model.task.ReadOnlyTask;
import tars.model.task.UniqueTaskList;
import tars.model.task.rsv.RsvTask;

import java.time.DateTimeException;
import java.util.HashMap;
import java.util.ArrayList;
import java.util.Set;
import java.util.Stack;

/**
 * The API of the Model component.
 */
public interface Model {
    /** Clears existing backing model and replaces with the provided new data. */
    void resetData(ReadOnlyTars newData);

    /** Returns the Tars */
    ReadOnlyTars getTars();

    /** Edits the given task and returns the edited task */
    Task editTask(ReadOnlyTask toEdit, HashMap<Flag, String> argsToEdit)
            throws UniqueTaskList.TaskNotFoundException, DateTimeException, IllegalValueException,
            TagNotFoundException;
    
    /** Undo an edited task */
    void unEditTask(Task toUndo, Task replacement) throws DuplicateTaskException;

    /** Deletes the given task. */
    void deleteTask(ReadOnlyTask target) throws UniqueTaskList.TaskNotFoundException;

    /** Adds the given task */
    void addTask(Task task) throws DuplicateTaskException;
    
    /** Adds the given reserved task */
    void addRsvTask(RsvTask rsvTask) throws DuplicateTaskException;

    /** Marks tasks as done or undone. */
    void mark(ArrayList<ReadOnlyTask> toMarkList, String status) throws DuplicateTaskException;

    /** Returns the filtered task list as an {@code UnmodifiableObservableList<ReadOnlyTask>} */
    UnmodifiableObservableList<ReadOnlyTask> getFilteredTaskList();

    /** Updates the filter of the filtered task list to show all tasks */
    void updateFilteredListToShowAll();

    /** Updates the filter of the filtered task list to filter by the given keywords*/
    void updateFilteredTaskList(Set<String> keywords);
    
    /** Updates the filter of the filtered task list to filter by the given keywords of each given
     * task attribute*/
    void updateFilteredTaskListUsingFlags(TaskQuery taskQuery);
    
    /** Updates the filter of the filtered task list to filter by the given keywords of a string 
     * consisting of all the attributes of each task*/
    void updateFilteredTaskListUsingQuickSearch(ArrayList<String> lazySearchKeywords);

    /** Returns the undoable command history stack */
    Stack<Command> getUndoableCmdHist();
    
    /** Returns the redoable command history stack */
    Stack<Command> getRedoableCmdHist();

   

}
