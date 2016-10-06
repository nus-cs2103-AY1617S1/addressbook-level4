package seedu.oneline.commons.events.model;

import seedu.oneline.commons.events.BaseEvent;
import seedu.oneline.model.ReadOnlyTaskBook;

/** Indicates the AddressBook in the model has changed*/
public class AddressBookChangedEvent extends BaseEvent {

    public final ReadOnlyTaskBook data;

    public AddressBookChangedEvent(ReadOnlyTaskBook data){
        this.data = data;
    }

    @Override
    public String toString() {
        return "number of persons " + data.getTaskList().size() + ", number of tags " + data.getTagList().size();
    }
}
