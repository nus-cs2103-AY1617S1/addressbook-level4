package seedu.address.model;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import seedu.address.commons.core.Messages;
import seedu.address.commons.exceptions.IllegalValueException;
import seedu.address.model.todo.ToDo;
import seedu.address.model.tag.Tag;
import seedu.address.model.tag.UniqueTagList;
import seedu.address.model.todo.DateRange;
import seedu.address.model.todo.DueDate;
import seedu.address.model.todo.ReadOnlyToDo;
import seedu.address.model.todo.Title;

import java.util.*;
import java.util.stream.Collectors;

/**
 * Represents a list of to-dos
 */
public class ToDoList implements ReadOnlyToDoList {

    private final ObservableList<ToDo> list;
    {
        list = FXCollections.observableArrayList();
    }

    public ToDoList() {}

    public ToDoList(ReadOnlyToDoList listToBeCopied) {
        resetData(listToBeCopied);
    }

    //================================================================================
    // List operations
    //================================================================================

    /**
     * Adds a to-do to the list
     * To-do is not copied, but is added to the list by reference
     */
    public void add(ToDo toDo) {
        assert toDo != null;
        list.add(toDo);
    }

    public boolean remove(ReadOnlyToDo toDo) throws IllegalValueException {
        if (list.remove(toDo)) {
            return true;
        } else {
            throw new IllegalValueException(String.format(Messages.MESSAGE_TODO_NOT_FOUND, toDo.toString()));
        }
    }

    public void setToDos(List<ToDo> ToDos) {
        list.setAll(ToDos);
    }
    

    public void resetData(Collection<? extends ReadOnlyToDo> newToDos) {
        setToDos(newToDos.stream().map(ToDo::new).collect(Collectors.toList()));
    }

    public void resetData(ReadOnlyToDoList newData) {
        resetData(newData.getToDoList());
    }

    public void editTitle(ReadOnlyToDo todo, Title title) throws IllegalValueException {
        getToDo(todo).setTitle(title);
    }

    public void editDateRange(ReadOnlyToDo todo, DateRange dateRange) throws IllegalValueException {
        getToDo(todo).setDateRange(dateRange);
    }

    public void editDueDate(ReadOnlyToDo todo, DueDate dueDates) throws IllegalValueException {
        getToDo(todo).setDueDate(dueDates);
    }

    public void editTags(ReadOnlyToDo todo, Set<Tag> tags) throws IllegalValueException {
        getToDo(todo).setTags(new UniqueTagList(tags));
    }
    
    
    //================================================================================
    // Utility methods
    //================================================================================

    public ToDo getToDo(ReadOnlyToDo todo) throws IllegalValueException {
        int idx = list.indexOf(todo);
        if (idx == -1){
            throw new IllegalValueException(String.format(Messages.MESSAGE_TODO_NOT_FOUND, todo.toString()));
        }
        return list.get(idx);
    }
    
    public ObservableList<ToDo> getToDos() {
        return list;
    }

    @Override
    public String toString() {
        return list.stream().map((toDo) -> toDo.toString()).collect(Collectors.joining(", "));
    }

    @Override
    public List<ReadOnlyToDo> getToDoList() {
        return Collections.unmodifiableList(list);
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof ToDoList // instanceof handles nulls
                && list.equals(((ToDoList) other).list));
    }

    @Override
    public int hashCode() {
        return Objects.hash(list);
    }

}
