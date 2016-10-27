package seedu.todoList.commons.events.model;

import seedu.todoList.commons.events.BaseEvent;
import seedu.todoList.model.ReadOnlyTaskList;

//@@author A0144061U
/** Indicates the TodoList in the model has changed*/
public class DeadlineListChangedEvent extends BaseEvent {

    public final ReadOnlyTaskList data;

    public DeadlineListChangedEvent(ReadOnlyTaskList data){
        this.data = data;
    }

    @Override
    public String toString() {
        return "number of tasks " + data.getTaskList().size();
    }
}