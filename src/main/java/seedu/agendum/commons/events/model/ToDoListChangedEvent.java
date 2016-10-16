package seedu.agendum.commons.events.model;

import seedu.agendum.commons.events.BaseEvent;
import seedu.agendum.model.ReadOnlyToDoList;

/** Indicates the ToDoList in the model has changed*/
public class ToDoListChangedEvent extends BaseEvent {

    public final ReadOnlyToDoList data;

    public ToDoListChangedEvent(ReadOnlyToDoList data){
        this.data = data;
    }

    @Override
    public String toString() {
        return "number of tasks " + data.getTaskList().size();
    }
}
