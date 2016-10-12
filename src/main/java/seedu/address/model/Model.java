package seedu.address.model;

import seedu.address.commons.core.UnmodifiableObservableList;
import seedu.address.model.person.ReadOnlyTask;
import seedu.address.model.person.Task;
import seedu.address.model.person.TaskList.DuplicateTaskException;
import seedu.address.model.person.TaskList.TaskNotFoundException;

import java.util.Set;

/**
 * The API of the Model component.
 */
public interface Model {
    /** Clears existing backing model and replaces with the provided new data. */
    void resetData(ReadOnlySavvyTasker newData);

    /** Returns the AddressBook */
    ReadOnlySavvyTasker getSavvyTasker();

    /** Deletes the given person. */
    void deleteTask(ReadOnlyTask target) throws TaskNotFoundException;

    /** Modifies the given task. */
    void modifyTask(ReadOnlyTask target, Task replacement) throws TaskNotFoundException;

    /** Adds the given person */
    void addTask(Task task) throws DuplicateTaskException;

    /** Returns the filtered person list as an {@code UnmodifiableObservableList<ReadOnlyPerson>} */
    UnmodifiableObservableList<ReadOnlyTask> getFilteredTaskList();

    /** Updates the filter of the filtered person list to show all persons */
    void updateFilteredListToShowAll();

    /** Updates the filter of the filtered person list to filter by the given keywords*/
    void updateFilteredTaskList(Set<String> keywords);

}
