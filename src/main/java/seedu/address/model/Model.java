package seedu.address.model;

import seedu.address.commons.core.UnmodifiableObservableList;
import seedu.address.model.person.FloatingTask;
import seedu.address.model.person.Title;
import seedu.address.model.person.Entry;
import seedu.address.model.person.UniquePersonList;
import seedu.address.model.person.UniquePersonList.DuplicateTaskException;
import seedu.address.model.person.UniquePersonList.PersonNotFoundException;

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
    void deleteTask(Entry target) throws UniquePersonList.PersonNotFoundException;

    /** Edit the given task */
    void editTask(Entry task, Title title) throws PersonNotFoundException, DuplicateTaskException;

    /** Adds the given task */
    void addTask(Entry entry) throws UniquePersonList.DuplicateTaskException;

    /** Returns the filtered person list as an {@code UnmodifiableObservableList<ReadOnlyPerson>} */
    UnmodifiableObservableList<Entry> getFilteredPersonList();

    /** Updates the filter of the filtered person list to show all persons */
    void updateFilteredListToShowAll();

    /** Updates the filter of the filtered person list to filter by the given keywords*/
    void updateFilteredPersonList(Set<String> keywords);

}
