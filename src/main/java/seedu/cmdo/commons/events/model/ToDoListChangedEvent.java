package seedu.cmdo.commons.events.model;

import seedu.cmdo.commons.events.BaseEvent;
import seedu.cmdo.model.ReadOnlyToDoList;

/** Indicates the CMDo in the model has changed*/
public class ToDoListChangedEvent extends BaseEvent {

    public final ReadOnlyToDoList data;

    public ToDoListChangedEvent(ReadOnlyToDoList data){
        this.data = data;
    }

    @Override
    public String toString() {
        return "details " + data.getTaskList().size() + ", number of tags " + data.getTagList().size();
    }
}
