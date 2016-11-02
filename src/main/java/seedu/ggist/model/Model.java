package seedu.ggist.model;

import java.util.Set;
import java.util.Stack;

import javafx.collections.transformation.FilteredList;
import seedu.ggist.commons.core.UnmodifiableObservableList;
import seedu.ggist.commons.exceptions.IllegalValueException;
import seedu.ggist.model.task.Task;
import seedu.ggist.model.task.ReadOnlyTask;
import seedu.ggist.model.task.UniqueTaskList;

/**
 * The API of the Model component.
 */
public interface Model {
    /** Clears existing backing model and replaces with the provided new data. */
    void resetData(ReadOnlyTaskManager newData);

    /** Returns the Task Manager */
    ReadOnlyTaskManager getTaskManager();

    /** Deletes the given task. */
    void deleteTask(ReadOnlyTask target) throws UniqueTaskList.TaskNotFoundException;
    
    /** Marks the given task as done. */
    void doneTask(ReadOnlyTask target) throws UniqueTaskList.TaskNotFoundException;

    /** Adds the given task */
    void addTask(Task task) throws UniqueTaskList.DuplicateTaskException;
    
    /** Edits the given task
     * @throws IllegalValueException */
    void editTask(Task target, String field, String value) throws UniqueTaskList.TaskNotFoundException, IllegalValueException;
    
    /** Returns the filtered task list as an {@code UnmodifiableObservableList<ReadOnlyTask>} */
    UnmodifiableObservableList<ReadOnlyTask> getFilteredTaskList();
    
    /** Returns the sorted task list as an {@code UnmodifiableObservableList<ReadOnlyTask>} */
    UnmodifiableObservableList<ReadOnlyTask> getSortedTaskList();

    /** Updates the filter of the filtered task list to show all tasks */
    void updateFilteredListToShowAll();
    
    /** Updates the filter of the filtered task list to show undone tasks */
    void updateFilteredListToShowAllUndone();

    /** Updates the filter of the filtered task list to filter by the given keywords*/
    void updateFilteredTaskList(Set<String> keywords);
    
    /** Updates the filter of the filtered task list to show all done tasks */
    void updateFilteredListToShowAllDone();
    
    /** Updates the filter of the filtered task list to reflect changes */
    void updateFilteredListToShowChanges();
    
    /** Updates the filter of the filtered task list to show task in a particular day */
    void updateFilteredListToShowDate(String keywords);

    /**Updates the attribute in ModelManager to reflect last shown listing */
    void setLastListing(String listing);
    
    /**Updates the filtered list */
    void updateListing();
    
    /**Returns the most recent type of listing of the filtered task list */
   String getLastListing();
   
   /**Updates the attribute in ModelManager to reflect priority listing */
   void updateFilteredListToPriority(String keywords);
   
 //@@author A0138420N
   /**Returns the list of commands that have been done */
   Stack<String> getListOfCommands();
   
   /**Returns the list of tasks that have been done */
   Stack<ReadOnlyTask> getListOfTasks();
   
   /**Returns the list of commands that have been redone */
   Stack<String> getRedoListOfCommands();
   
   /**Returns the list of tasks that have been redone */
   Stack<ReadOnlyTask> getRedoListOfTasks();
   
   /**Returns the list of fields that have been edited */
   Stack<String> getEditTaskField();
   
   /**Returns the list of field values that have been edited */
   Stack<String> getEditTaskValue();
   
   /**Returns the list of fields that have been re-edited */
   Stack<String> getRedoEditTaskField();
   
   /**Returns the list of field value that have been re-edited */
   Stack<String> getRedoEditTaskValue();
   
   /**Empties all the stacks that store undo and redo commands and tasks */
   void clearStacks();
 //@@author
   
}
