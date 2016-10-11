package seedu.address.model;

import seedu.address.commons.core.UnmodifiableObservableList;
import seedu.address.model.item.Task;
import seedu.address.model.item.ReadOnlyTask;
import seedu.address.model.item.UniqueTaskList;

import java.util.Set;

/**
 * The API of the Model component.
 */
public interface Model {
    /** Clears existing backing model and replaces with the provided new data. */
    void resetData(ReadOnlyTaskManager newData);

    /** Returns the TaskManager */
    ReadOnlyTaskManager getTaskManager();

    /** Deletes the given floating task. */
    void deleteTask(ReadOnlyTask target) throws UniqueTaskList.TaskNotFoundException;

    /** Adds the given floating task */
    void addTask(Task task) throws UniqueTaskList.DuplicateTaskException;

    /** Returns the filtered floating task list as an {@code UnmodifiableObservableList<ReadOnlyPerson>} */
    UnmodifiableObservableList<ReadOnlyTask> getFilteredFloatingTaskList();

    /** Updates the filter of the filtered floating task list to show all floating tasks */
    void updateFilteredListToShowAll();

    /** Updates the filter of the filtered floating task list to filter by the given keywords*/
    void updateFilteredFloatingTaskList(Set<String> keywords);



}
