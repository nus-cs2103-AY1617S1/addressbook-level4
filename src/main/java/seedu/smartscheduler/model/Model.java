package seedu.smartscheduler.model;

import java.util.Set;

import seedu.smartscheduler.commons.core.UnmodifiableObservableList;
import seedu.smartscheduler.model.task.ReadOnlyTask;
import seedu.smartscheduler.model.task.Task;
import seedu.smartscheduler.model.task.UniqueTaskList;

/**
 * The API of the Model component.
 */
public interface Model {
    /** Clears existing backing model and replaces with the provided new data. */
    void resetData(ReadOnlyTaskList newData);

    /** Returns the AddressBook */
    ReadOnlyTaskList getAddressBook();

    /** Deletes the given person. */
    void deletePerson(ReadOnlyTask target) throws UniqueTaskList.PersonNotFoundException;

    /** Adds the given person */
    void addPerson(Task task) throws UniqueTaskList.DuplicatePersonException;
    
    /** Adds a note */
    void addNote(Task task);

    /** Returns the filtered person list as an {@code UnmodifiableObservableList<ReadOnlyPerson>} */
    UnmodifiableObservableList<ReadOnlyTask> getFilteredPersonList();

    /** Updates the filter of the filtered person list to show all persons */
    void updateFilteredListToShowAll();

    /** Updates the filter of the filtered person list to filter by the given keywords*/
    void updateFilteredPersonList(Set<String> keywords);

}
