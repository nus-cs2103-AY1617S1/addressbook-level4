package seedu.oneline.model;

import java.util.Set;

import seedu.oneline.commons.core.UnmodifiableObservableList;
import seedu.oneline.model.task.ReadOnlyTask;
import seedu.oneline.model.task.Task;
import seedu.oneline.model.task.TaskName;
import seedu.oneline.model.task.UniqueTaskList;

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

    /** Returns the filtered task list as an {@code UnmodifiableObservableList<ReadOnlyTask>} */
    UnmodifiableObservableList<ReadOnlyTask> getFilteredTaskList();

    /** Updates the filter of the filtered task list to show all tasks */
    void updateFilteredListToShowAll();
    
    /** Updates the filter of the filtered task list to show all tasks that are not done */
    void updateFilteredListToShowAllNotDone();
    
    /** Updates the filter of the filtered task list to show all tasks that are done */
    void updateFilteredListToShowAllDone();

    /** Updates the filter of the filtered task list to filter by the given keywords*/
    void updateFilteredTaskList(Set<String> keywords);








}
