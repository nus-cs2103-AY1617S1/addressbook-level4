package seedu.cmdo.commons.events.model;

import seedu.cmdo.commons.events.BaseEvent;
import seedu.cmdo.model.ReadOnlyToDoList;

/** Indicates the AddressBook in the model has changed*/
public class ToDoListChangedEvent extends BaseEvent {

    public final ReadOnlyToDoList data;

    public ToDoListChangedEvent(ReadOnlyToDoList data){
        this.data = data;
    }

    @Override
    public String toString() {
        return "number of persons " + data.getTaskList().size() + ", number of tags " + data.getTagList().size();
    }
}
