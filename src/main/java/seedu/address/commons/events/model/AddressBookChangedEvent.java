package seedu.address.commons.events.model;

import seedu.address.commons.events.BaseEvent;
import seedu.address.model.ReadOnlyToDoList;

/** Indicates the ToDoList in the model has changed*/
public class AddressBookChangedEvent extends BaseEvent {

    public final ReadOnlyToDoList data;

    public AddressBookChangedEvent(ReadOnlyToDoList data){
        this.data = data;
    }

    @Override
    public String toString() {
        return "number of persons " + data.getTaskList().size() + ", number of tags " + data.getTagList().size();
    }
}
