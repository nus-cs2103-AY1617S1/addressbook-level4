package seedu.unburden.commons.events.model;

import seedu.unburden.commons.events.BaseEvent;
import seedu.unburden.model.ReadOnlyAddressBook;

/** Indicates the ListOfTask in the model has changed*/
public class AddressBookChangedEvent extends BaseEvent {

    public final ReadOnlyAddressBook data;

    public AddressBookChangedEvent(ReadOnlyAddressBook data){
        this.data = data;
    }

    @Override
    public String toString() {
        return "number of persons " + data.getPersonList().size() + ", number of tags " + data.getTagList().size();
    }
}
