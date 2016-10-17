package seedu.gtd.commons.events.model;

import seedu.gtd.commons.events.BaseEvent;
import seedu.gtd.model.ReadOnlyAddressBook;

/** Indicates the AddressBook in the model has changed*/
public class AddressBookChangedEvent extends BaseEvent {

    public final ReadOnlyAddressBook data;

    public AddressBookChangedEvent(ReadOnlyAddressBook data){
        this.data = data;
    }

    @Override
    public String toString() {
        return "number of persons " + data.getTaskList().size() + ", number of tags " + data.getTagList().size();
    }
}
