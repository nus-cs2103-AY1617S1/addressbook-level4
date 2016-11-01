package w15c2.tusk.commons.events.model;

import w15c2.tusk.commons.collections.UniqueItemCollection;
import w15c2.tusk.commons.events.BaseEvent;
import w15c2.tusk.model.Alias;

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
