package seedu.Tdoo.commons.events.model;

import seedu.Tdoo.commons.events.BaseEvent;
import seedu.Tdoo.model.ReadOnlyTaskList;

//@@author A0144061U
/** Indicates the TodoList in the model has changed*/
public class TodoListChangedEvent extends BaseEvent {

    public final ReadOnlyTaskList data;

    public TodoListChangedEvent(ReadOnlyTaskList data){
        this.data = data;
    }

    @Override
    public String toString() {
        return "number of tasks " + data.getTaskList().size();
    }
}