package seedu.taskmanager.commons.events.model;

import seedu.taskmanager.commons.events.BaseEvent;
import seedu.taskmanager.model.ReadOnlyAddressBook;

/** Indicates the AddressBook in the model has changed*/
public class TaskManagerChangedEvent extends BaseEvent {

    public final ReadOnlyAddressBook data;

    public TaskManagerChangedEvent(ReadOnlyAddressBook data){
        this.data = data;
    }

    @Override
    public String toString() {
        return "number of tasks " + data.getPersonList().size() + ", number of tags " + data.getTagList().size();
    }
}
