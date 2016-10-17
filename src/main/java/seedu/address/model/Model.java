package seedu.address.model;

import seedu.address.commons.core.UnmodifiableObservableList;
import seedu.address.commons.exceptions.IllegalValueException;
import seedu.address.model.task.ReadOnlyTask;
import seedu.address.model.task.Task;
import seedu.address.model.task.UniqueTaskList;
import seedu.address.model.task.UniqueTaskList.PersonNotFoundException;

import java.util.Set;

/**
 * The API of the Model component.
 */
public interface Model {
    /** Clears existing backing model and replaces with the provided new data. */
    void resetData(ReadOnlyTaskBook newData);

    /** Returns the AddressBook */
    ReadOnlyTaskBook getAddressBook();

    /** Deletes the given person. */
    void deletePerson(ReadOnlyTask target) throws UniqueTaskList.PersonNotFoundException;

    /** Adds the given person */
    void addTask(Task person) throws UniqueTaskList.DuplicatePersonException;

    /** Returns the filtered person list as an {@code UnmodifiableObservableList<ReadOnlyPerson>} */
    UnmodifiableObservableList<ReadOnlyTask> getFilteredPersonList();
    UnmodifiableObservableList<ReadOnlyTask> getFilteredDeadlineList();
    UnmodifiableObservableList<ReadOnlyTask> getFilteredTodoList();
    
    /** Updates the filter of the filtered person list to show all persons */
    void updateFilteredListToShowAll();

    /** Updates the filter of the filtered person list to filter by the given keywords*/
    void updateFilteredPersonList(Set<String> keywords);
    void updateFilteredDeadlineList(Set<String> keywords);
    void updateFilteredTodoList(Set<String> keywords);

    /** Edits the given person 
     * @throws IllegalValueException 
     * @throws PersonNotFoundException */
    void editPerson(ReadOnlyTask target, String args, char category) throws PersonNotFoundException, IllegalValueException;

}
