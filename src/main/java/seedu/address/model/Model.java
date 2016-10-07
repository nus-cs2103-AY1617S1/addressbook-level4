package seedu.address.model;

import seedu.address.commons.core.UnmodifiableObservableList;
import seedu.address.model.task.ReadOnlyTask;
import seedu.address.model.task.Task;
import seedu.address.model.task.UniquePersonList;
import seedu.address.model.task.UniquePersonList.DuplicatePersonException;
import seedu.address.model.task.UniquePersonList.PersonNotFoundException;

import java.util.Set;

/**
 * The API of the Model component.
 */
public interface Model {
    /** Clears existing backing model and replaces with the provided new data. */
    void resetData(ReadOnlyAddressBook newData);

    /** Returns the AddressBook */
    ReadOnlyAddressBook getAddressBook();

    /** Deletes the given task. */
    void deleteTask(ReadOnlyTask target) throws UniquePersonList.PersonNotFoundException;

    /** Adds the given task */
    void addTask(Task task) throws UniquePersonList.DuplicatePersonException;

    /** Edits the given task. 
     * @throws DuplicatePersonException */
    void editTask(ReadOnlyTask target, Task task) throws PersonNotFoundException, DuplicatePersonException;

    /** Returns the filtered person list as an {@code UnmodifiableObservableList<ReadOnlyPerson>} */
    UnmodifiableObservableList<ReadOnlyTask> getFilteredPersonList();

    /** Updates the filter of the filtered person list to show all persons */
    void updateFilteredListToShowAll();

    /** Updates the filter of the filtered person list to filter by the given keywords*/
    void updateFilteredPersonList(Set<String> keywords);

}
