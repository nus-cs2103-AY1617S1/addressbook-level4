package harmony.model;

import java.util.Set;
import java.util.Stack;

import harmony.commons.core.UnmodifiableObservableList;
import harmony.logic.commands.Command;
import harmony.model.tag.Tag;
import harmony.model.task.ReadOnlyTask;
import harmony.model.task.Task;
import harmony.model.task.UniqueTaskList;

/**
 * The API of the Model component.
 */
public interface Model {
    /** Clears existing backing model and replaces with the provided new data. */
    void resetData(ReadOnlyTaskManager newData);

    /** Returns the AddressBook */
    ReadOnlyTaskManager getAddressBook();

    /** Deletes the given person. */
    void deletePerson(ReadOnlyTask target) throws UniqueTaskList.PersonNotFoundException;

    /** Adds the given person */
    void addPerson(Task person) throws UniqueTaskList.DuplicatePersonException;
    
    /** Returns the stack of command history */
    Stack<Command> getCommandHistory();

    /** Returns the filtered person list as an {@code UnmodifiableObservableList<ReadOnlyPerson>} */
    UnmodifiableObservableList<ReadOnlyTask> getFilteredPersonList();

    /** Updates the filter of the filtered person list to show all persons */
    void updateFilteredListToShowAll();

    /** Updates the filter of the filtered person list to filter by the given keywords*/
    void updateFilteredPersonList(Set<String> keywords);

    /** Updates the filter of the filtered person list to filter by the given keywords*/
    void updateFilteredTagPersonList(Set<Tag> keywords);

}
