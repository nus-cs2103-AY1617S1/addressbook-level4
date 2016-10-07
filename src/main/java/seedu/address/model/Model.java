package seedu.address.model;

import seedu.address.commons.core.UnmodifiableObservableList;
import seedu.address.commons.exceptions.IllegalValueException;
import seedu.address.model.todo.ToDo;
import seedu.address.model.tag.Tag;
import seedu.address.model.todo.DateRange;
import seedu.address.model.todo.DueDate;
import seedu.address.model.todo.ReadOnlyToDo;
import seedu.address.model.todo.Title;

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
    
    /** Edits the title of given to-do */
    void editTodoTitle(ReadOnlyToDo todo, Title title) throws IllegalValueException;
    
    /** Edits the Date Range of given to-do */
    void editTodoDateRange(ReadOnlyToDo todo, DateRange dateRange) throws IllegalValueException;
    
    /** Edits the Due date of given to-do */
    void editTodoDueDate(ReadOnlyToDo todo, DueDate dueDates) throws IllegalValueException;    

    /** Edits the tags of given to-do */
    void editTodoTags(ReadOnlyToDo todo, Set<Tag> tags) throws IllegalValueException;
      
    /** Returns the filtered to-do list as an {@code UnmodifiableObservableList<ReadOnlyToDo>} */
    UnmodifiableObservableList<ReadOnlyToDo> getFilteredToDoList();

    /** Updates the filter of the filtered to-do list to show all to-dos */
    void updateFilteredListToShowAll();

    /** Updates the filter of the filtered to-do list to filter by the given keywords */
    void updateFilteredToDoList(Set<String> keywords);

}
