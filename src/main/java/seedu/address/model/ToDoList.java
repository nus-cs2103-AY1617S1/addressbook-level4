package seedu.address.model;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import seedu.address.commons.exceptions.IllegalValueException;
import seedu.address.model.todo.ToDo;
import seedu.address.model.todo.ReadOnlyToDo;

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
            throw new IllegalValueException(toDo.toString() + " not found in to-do list");
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


    //================================================================================
    // Utility methods
    //================================================================================

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
