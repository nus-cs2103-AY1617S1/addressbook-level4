package seedu.oneline.model;

import java.util.Set;

import seedu.oneline.commons.core.UnmodifiableObservableList;
import seedu.oneline.commons.exceptions.StateNonExistentException;
import seedu.oneline.logic.commands.Command;
import seedu.oneline.logic.commands.CommandResult;
import seedu.oneline.model.task.ReadOnlyTask;
import seedu.oneline.model.task.Task;
import seedu.oneline.model.task.TaskName;
import seedu.oneline.model.task.UniqueTaskList;
import seedu.oneline.model.tag.Tag; 

/**
 * The API of the Model component.
 */
public interface Model {
    /** Clears existing backing model and replaces with the provided new data. */
    void resetData(ReadOnlyTaskBook newData);

    /** Returns the Task book */
    ReadOnlyTaskBook getTaskBook();

    /** Deletes the given task. */
    void deleteTask(ReadOnlyTask target) throws UniqueTaskList.TaskNotFoundException;

    /** Adds the given task */
    void addTask(Task task) throws UniqueTaskList.DuplicateTaskException;

    /** Updates the given task */
    void replaceTask(ReadOnlyTask oldTask, Task newTask) throws UniqueTaskList.TaskNotFoundException, UniqueTaskList.DuplicateTaskException;
    
    /** Marks a given task as done */
    void doneTask(int index) throws UniqueTaskList.TaskNotFoundException;
    
    /** Marks a given task as not done */
    void undoneTask(int index) throws UniqueTaskList.TaskNotFoundException;

    /** Returns the tag list as an {@code UnmodifiableObservableList<Tag>} */
    UnmodifiableObservableList<Tag> getTagList(); 
    
    /** Returns the filtered task list as an {@code UnmodifiableObservableList<ReadOnlyTask>} */
    UnmodifiableObservableList<ReadOnlyTask> getFilteredTaskList();
    
    /** Updates the filter of the filtered task list to show all tasks */
    void updateFilteredListToShowAll();
    
    /** Updates the filter of the filtered task list to show all tasks that are not done */
    void updateFilteredListToShowAllNotDone();
    
    /** Updates the filter of the filtered task list to show all tasks that are done */
    void updateFilteredListToShowAllDone();

    /** Updates the filter of the filtered task list to show all tasks that are due today */    
    public void updateFilteredListToShowToday();

    /** Updates the filter of the filtered task list to show all tasks that are due in the coming 7 days*/ 
    public void updateFilteredListToShowWeek();

    /** Updates the filter of the filtered task list to show all floating tasks */ 
    public void updateFilteredListToShowFloat();
    
    /** Updates the filter of the filtered task list to filter by the given keywords*/
    void updateFilteredTaskList(Set<String> keywords);
    
    /** Executes the command on the current model, and returns the result*/
    CommandResult executeCommand(Command command);
    
    /** Reverts to the previous model state*/
    void undo() throws StateNonExistentException;
    
    /** Reverts to the state before the last undone*/
    void redo() throws StateNonExistentException;
}
