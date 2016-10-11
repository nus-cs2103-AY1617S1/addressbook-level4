package seedu.address.model;

import java.util.function.Predicate;

import seedu.address.commons.core.UnmodifiableObservableList;
import seedu.address.model.task.Task;
import seedu.address.model.task.TaskNotFoundException;

/**
 * The API of the Model component.
 */
public interface Model {
    /** Clears existing backing model and replaces with the provided new data. */
    void resetData(ReadOnlyTaskBook newData);

    /** Returns the TaskBook */
    ReadOnlyTaskBook getAddressBook();

    /** Deletes the given task. */
    void deleteTask(Task target) throws TaskNotFoundException;

    /** Adds the given task */
    void addTask(Task task);

    /** Returns the filtered task list as an {@code UnmodifiableObservableList<Task>} */
    UnmodifiableObservableList<Task> getFilteredTaskList();

    /**
     * Updates the filter of the filtered task list to filter by the given predicate.
     *
     * If predicate is null, all tasks will be shown.
     */
    void setFilter(Predicate<Task> predicate);

}
