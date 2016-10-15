package seedu.emeraldo.model;

import seedu.emeraldo.commons.core.UnmodifiableObservableList;
import seedu.emeraldo.model.task.ReadOnlyTask;
import seedu.emeraldo.model.task.Task;
import seedu.emeraldo.model.task.UniquePersonList;

import java.util.Set;

/**
 * The API of the Model component.
 */
public interface Model {
    /** Clears existing backing model and replaces with the provided new data. */
    void resetData(ReadOnlyEmeraldo newData);

    /** Returns the AddressBook */
    ReadOnlyEmeraldo getAddressBook();

    /** Deletes the given person. */
    void deleteTask(ReadOnlyTask target) throws UniquePersonList.TaskNotFoundException;

    /** Adds the given person */
    void addTask(Task person) throws UniquePersonList.DuplicateTaskException;

    /** Returns the filtered person list as an {@code UnmodifiableObservableList<ReadOnlyPerson>} */
    UnmodifiableObservableList<ReadOnlyTask> getFilteredTaskList();

    /** Updates the filter of the filtered person list to show all persons */
    void updateFilteredListToShowAll();

    /** Updates the filter of the filtered person list to filter by the given keywords*/
    void updateFilteredTaskList(Set<String> keywords);

}
