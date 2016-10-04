package tars.model;

import tars.commons.core.UnmodifiableObservableList;
import tars.model.task.Person;
import tars.model.task.ReadOnlyPerson;
import tars.model.task.UniquePersonList;

import java.util.Set;

/**
 * The API of the Model component.
 */
public interface Model {
    /** Clears existing backing model and replaces with the provided new data. */
    void resetData(ReadOnlyTars newData);

    /** Returns the Tars */
    ReadOnlyTars getTars();

    /** Deletes the given task. */
    void deletePerson(ReadOnlyPerson target) throws UniquePersonList.PersonNotFoundException;

    /** Adds the given task */
    void addPerson(Person task) throws UniquePersonList.DuplicatePersonException;

    /** Returns the filtered task list as an {@code UnmodifiableObservableList<ReadOnlyPerson>} */
    UnmodifiableObservableList<ReadOnlyPerson> getFilteredPersonList();

    /** Updates the filter of the filtered task list to show all tasks */
    void updateFilteredListToShowAll();

    /** Updates the filter of the filtered task list to filter by the given keywords*/
    void updateFilteredPersonList(Set<String> keywords);

}
