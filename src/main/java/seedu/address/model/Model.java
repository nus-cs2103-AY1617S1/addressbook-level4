package seedu.address.model;

import seedu.address.commons.core.UnmodifiableObservableList;
import seedu.address.model.todo.ToDo;
import seedu.address.model.todo.ReadOnlyToDo;
import seedu.address.model.todo.UniqueToDoList;

import java.util.Set;

/**
 * The API of the Model component.
 */
public interface Model {
    /** Clears existing backing model and replaces with the provided new data. */
    void resetData(ReadOnlyToDoList newData);

    /** Returns the ToDoList */
    ReadOnlyToDoList getToDoList();

    /** Deletes the given ToDo. */
    void deleteToDo(ReadOnlyToDo target) throws UniqueToDoList.ToDoNotFoundException;

    /** Adds the given ToDo */
    void addToDo(ToDo toDo) throws UniqueToDoList.DuplicateToDoException;

    /** Returns the filtered ToDo list as an {@code UnmodifiableObservableList<ReadOnlyToDo>} */
    UnmodifiableObservableList<ReadOnlyToDo> getFilteredToDoList();

    /** Updates the filter of the filtered ToDo list to show all ToDos */
    void updateFilteredListToShowAll();

    /** Updates the filter of the filtered ToDo list to filter by the given keywords*/
    void updateFilteredToDoList(Set<String> keywords);

}
