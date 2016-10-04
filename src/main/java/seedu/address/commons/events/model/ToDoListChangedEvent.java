package seedu.address.commons.events.model;

import seedu.address.commons.events.BaseEvent;
import seedu.address.model.ReadOnlyToDoList;

/**
 * Indicates to-do list in the model has changed
 */
public class ToDoListChangedEvent extends BaseEvent {

    public final ReadOnlyToDoList toDoList;

    public ToDoListChangedEvent(ReadOnlyToDoList toDoList){
        this.toDoList = toDoList;
    }

    @Override
    public String toString() {
        return this.getClass().getSimpleName() + " with to-do list: " + toDoList.toString();
    }
}