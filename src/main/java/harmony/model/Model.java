package harmony.model;

import java.util.Set;
import java.util.Stack;

import harmony.commons.core.UnmodifiableObservableList;
import harmony.logic.commands.Command;
import harmony.model.person.Person;
import harmony.model.person.ReadOnlyPerson;
import harmony.model.person.UniquePersonList;
import harmony.model.tag.Tag;

/**
 * The API of the Model component.
 */
public interface Model {
    /** Clears existing backing model and replaces with the provided new data. */
    void resetData(ReadOnlyAddressBook newData);

    /** Returns the AddressBook */
    ReadOnlyAddressBook getAddressBook();

    /** Deletes the given person. */
    void deletePerson(ReadOnlyPerson target) throws UniquePersonList.PersonNotFoundException;

    /** Adds the given person */
    void addPerson(Person person) throws UniquePersonList.DuplicatePersonException;
    
    /** Returns the stack of command history */
    Stack<Command> getCommandHistory();

    /** Returns the filtered person list as an {@code UnmodifiableObservableList<ReadOnlyPerson>} */
    UnmodifiableObservableList<ReadOnlyPerson> getFilteredPersonList();

    /** Updates the filter of the filtered person list to show all persons */
    void updateFilteredListToShowAll();

    /** Updates the filter of the filtered person list to filter by the given keywords*/
    void updateFilteredPersonList(Set<String> keywords);

    /** Updates the filter of the filtered person list to filter by the given keywords*/
    void updateFilteredTagPersonList(Set<Tag> keywords);

}
