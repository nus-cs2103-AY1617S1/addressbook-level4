package taskle.model;

import java.util.Set;


import taskle.commons.core.ModifiableObservableList;
import taskle.commons.core.UnmodifiableObservableList;
import taskle.model.person.ModifiableTask;
import taskle.model.person.Name;
import taskle.model.person.ReadOnlyTask;
import taskle.model.person.Task;
import taskle.model.person.UniqueTaskList;

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

    /** Edits the given task. */
    void editTask(ModifiableTask target, Name newName) throws UniqueTaskList.TaskNotFoundException, UniqueTaskList.DuplicateTaskException;
    
    /** Adds the given task */
    void addTask(Task task) throws UniqueTaskList.DuplicateTaskException;

    /** Returns the filtered task list as an {@code UnmodifiableObservableList<ReadOnlyTask>} */
    UnmodifiableObservableList<ReadOnlyTask> getFilteredTaskList();

    /** Returns the filtered task list as an {@code ModifiableObservableList<ReadOnlyTask>} */
    ModifiableObservableList<ModifiableTask> getModifiableTaskList();
    
    /** Updates the filter of the filtered task list to show all tasks */
    void updateFilteredListToShowAll();

    /** Updates the filter of the filtered task list to filter by the given keywords*/
    void updateFilteredTaskList(Set<String> keywords);

}
