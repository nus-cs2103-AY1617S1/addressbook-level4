package seedu.oneline.model;

import java.util.Map.Entry;
import java.util.Set;

import com.sun.javafx.collections.UnmodifiableObservableMap;

import seedu.oneline.commons.core.UnmodifiableObservableList;
import seedu.oneline.commons.exceptions.StateNonExistentException;
import seedu.oneline.logic.commands.Command;
import seedu.oneline.logic.commands.CommandResult;
import seedu.oneline.model.task.ReadOnlyTask;
import seedu.oneline.model.task.Task;
import seedu.oneline.model.task.TaskName;
import seedu.oneline.model.task.UniqueTaskList;
import seedu.oneline.model.tag.Tag;
import seedu.oneline.model.tag.TagColor;
import seedu.oneline.model.tag.TagColorMap; 
import seedu.oneline.model.task.UniqueTaskList.DuplicateTaskException;
import seedu.oneline.model.task.UniqueTaskList.TaskNotFoundException;

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
    
    /** Updates the given task, for use to mark task as undone */
    void replaceUndoneTask(ReadOnlyTask oldTask, Task newTask) throws TaskNotFoundException, DuplicateTaskException;

    /** Returns the tag list as an {@code UnmodifiableObservableList<Tag>} */
    UnmodifiableObservableList<Tag> getTagList(); 
    
    /** Returns the filtered task list as an {@code UnmodifiableObservableList<ReadOnlyTask>} */
    UnmodifiableObservableList<ReadOnlyTask> getFilteredTaskList();
    
    /** Get the assigned color to the tag */
    TagColor getTagColor(Tag t);
    
    /** Assigns a color to the tag */
    void setTagColor(Tag t, TagColor c);
    
    /** Returns the tag color mapping as an {@code UnmodifiableObservableMap<Tag, TagColor>} */
    TagColorMap getTagColorMap();
    
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
