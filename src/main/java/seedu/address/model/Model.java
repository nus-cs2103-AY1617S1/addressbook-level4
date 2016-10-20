package seedu.address.model;

import seedu.address.commons.core.UnmodifiableObservableList;
import seedu.address.model.task.Task;
import seedu.address.model.task.ReadOnlyTask;
import seedu.address.model.task.UniqueTaskList;

import java.util.ArrayList;
import java.util.Set;
import java.util.function.Predicate;

/**
 * The API of the Model component.
 */
public interface Model {
    /** Clears existing backing model and replaces with the provided new data. */
    void resetData(ReadOnlyTaskManager newData);

    /** Returns the TaskManager */
    ReadOnlyTaskManager getTaskManager();
    
    /** Saves the state of the model in case the user wishes to undo an action. */
    void saveState();
    
    /** Reverts back to previous task and tag list before the last command was executed. */
    void loadPreviousState();
    
    /** Redoes an action after an undo. */
    void loadNextState();

    /** Deletes the given tasks. */
    void deleteTasks(ArrayList<ReadOnlyTask> targets) throws UniqueTaskList.TaskNotFoundException;

    /** Adds the given task */
    void addTask(Task task) throws UniqueTaskList.DuplicateTaskException;
    
    /** Updates the given task */
    void editTask(int index, Task task) throws UniqueTaskList.TaskNotFoundException;
    
    /** Returns the filtered task list as an {@code UnmodifiableObservableList<ReadOnlyTask>} */
    UnmodifiableObservableList<ReadOnlyTask> getFilteredTaskList();

    /** Updates the filter of the filtered task list to show all tasks */
    void updateFilteredListToShowAll();

    /** Updates the filter of the filtered task list to filter by the given keywords*/
    void updateFilteredTaskList(Set<String> keywords);

	void updateFilteredTaskList(Predicate<ReadOnlyTask> somedayTask);

}
