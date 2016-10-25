package seedu.address.commons.events.model;

import seedu.address.commons.collections.UniqueItemCollection;
import seedu.address.commons.events.BaseEvent;
import seedu.address.model.Alias;

/** Indicates the AddressBook in the model has changed*/
//@@author A0143107U
public class AliasChangedEvent extends BaseEvent {

    public final UniqueItemCollection<Alias> data;

    public AliasChangedEvent(UniqueItemCollection<Alias> data){
        this.data = data;
    }

    @Override
    public String toString() {
        return "number of alias " + data.getInternalList().size();
    }
}
