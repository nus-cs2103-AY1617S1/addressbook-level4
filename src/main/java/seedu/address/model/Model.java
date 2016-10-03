package seedu.address.model;

import seedu.address.commons.core.UnmodifiableObservableList;
import seedu.address.commons.exceptions.IllegalValueException;
import seedu.address.model.todo.ToDo;
import seedu.address.model.todo.ReadOnlyToDo;

import java.util.Set;

/**
 * The API of the Model component.
 */
public interface Model {
    /** Clears existing backing model and replaces with the provided new data */
    void resetData(ReadOnlyToDoList newToDoList);

    /** Returns the to-do list */
    ReadOnlyToDoList getToDoList();

    /** Deletes the given to-do */
    void deleteToDo(ReadOnlyToDo toDo) throws IllegalValueException;

    /** Adds the given to-do */
    void addToDo(ToDo toDo);

    /** Returns the filtered to-do list as an {@code UnmodifiableObservableList<ReadOnlyToDo>} */
    UnmodifiableObservableList<ReadOnlyToDo> getFilteredToDoList();

    /** Updates the filter of the filtered to-do list to show all to-dos */
    void updateFilteredListToShowAll();

    /** Updates the filter of the filtered to-do list to filter by the given keywords */
    void updateFilteredToDoList(Set<String> keywords);

}
