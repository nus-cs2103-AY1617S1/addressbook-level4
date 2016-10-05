package seedu.address.commons.events.model;

import seedu.address.commons.events.BaseEvent;
import seedu.address.model.ReadOnlyActivityManager;

/** Indicates the ActivityManager in the model has changed*/
public class AddressBookChangedEvent extends BaseEvent {

    public final ReadOnlyActivityManager data;

    public AddressBookChangedEvent(ReadOnlyActivityManager data){
        this.data = data;
    }

    @Override
    public String toString() {
        return "number of persons " + data.getListActivity().size() + ", number of tags " + data.getTagList().size();
    }
}
