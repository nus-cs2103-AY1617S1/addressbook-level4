package seedu.agendum.commons.events.model;

import seedu.agendum.commons.events.BaseEvent;
import seedu.agendum.model.ReadOnlyToDoList;

/** Indicates the ToDoList in the model has changed*/
public class ToDoListChangedEvent extends BaseEvent {

    public final ReadOnlyToDoList data;
    
    private String message;

    public ToDoListChangedEvent(ReadOnlyToDoList data){
        this.data = data;
        this.message =  "number of tasks " + data.getTaskList().size();
    }

    @Override
    public String toString() {
        return message;
    }
}
