package seedu.address.commons.events.model;

import seedu.address.commons.events.BaseEvent;
import seedu.address.model.ReadOnlyTaskScheduler;

/** Indicates the AddressBook in the model has changed*/
public class AddressBookChangedEvent extends BaseEvent {

    public final ReadOnlyTaskScheduler data;

    public AddressBookChangedEvent(ReadOnlyTaskScheduler data){
        this.data = data;
    }

    @Override
    public String toString() {
        return "number of persons " + data.getTaskList().size() + ", number of tags " + data.getTagList().size();
    }
}
