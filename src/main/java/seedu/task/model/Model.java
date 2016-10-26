package seedu.task.model;

import java.util.Set;

import seedu.task.commons.core.UnmodifiableObservableList;
import seedu.task.logic.commands.Command;
import seedu.task.model.task.ReadOnlyTask;
import seedu.task.model.task.Task;
import seedu.task.model.task.UniqueTaskList;

/**
 * The API of the Model component.
 */
public interface Model {

    /** Clears existing backing model and replaces with the provided new data. */
    void resetData(ReadOnlyTaskManager newData);

    /** Returns the TaskManager */
    ReadOnlyTaskManager getTaskManager();

    /** Deletes the given task. */
    void deleteTask(ReadOnlyTask target) throws UniqueTaskList.TaskNotFoundException;
    
    /** Deletes the given task. */
    void completeTask(ReadOnlyTask target, ReadOnlyTask toBeComplete) throws UniqueTaskList.TaskNotFoundException;

    /** Adds the given task */
    void addTask(Task task) throws UniqueTaskList.DuplicateTaskException;
    
    /** Adds the given task at specified index of the list in the data, not the index shown to the user*/
    void addTaskWithSpecifiedIndex(Task task, int index) throws UniqueTaskList.DuplicateTaskException;
   
    /** Returns the filtered task list as an {@code UnmodifiableObservableList<ReadOnlyTask>} */
    UnmodifiableObservableList<ReadOnlyTask> getFilteredTaskList();

    /** Updates the filter of the filtered task list to show all tasks */
    void updateFilteredListToShowAll();

    /** Updates the filter of the filtered task list to filter by the given keywords*/
    void updateFilteredTaskList(Set<String> keywords);
    
    //@@author A0153411W
    /** Updates Commands For Undo Stack with new executed command*/
    void updateCommandsForUndo(Command commandForUndo);
    
    /** Get Command for undo*/
    Command getCommandForUndo(); 
}
