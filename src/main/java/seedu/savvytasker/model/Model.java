package seedu.savvytasker.model;

import java.util.Set;

import seedu.savvytasker.commons.core.UnmodifiableObservableList;
import seedu.savvytasker.model.person.ReadOnlyTask;
import seedu.savvytasker.model.person.Task;
import seedu.savvytasker.model.person.TaskList.DuplicateTaskException;
import seedu.savvytasker.model.person.TaskList.TaskNotFoundException;

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
