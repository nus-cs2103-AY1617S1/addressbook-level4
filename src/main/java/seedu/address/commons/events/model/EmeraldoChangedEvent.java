package seedu.address.commons.events.model;

import seedu.address.model.ReadOnlyEmeraldo;
import seedu.emeraldo.commons.events.BaseEvent;

/** Indicates the AddressBook in the model has changed*/
public class EmeraldoChangedEvent extends BaseEvent {

    public final ReadOnlyEmeraldo data;

    public EmeraldoChangedEvent(ReadOnlyEmeraldo data){
        this.data = data;
    }

    @Override
    public String toString() {
        return "number of persons " + data.getPersonList().size() + ", number of tags " + data.getTagList().size();
    }
}
