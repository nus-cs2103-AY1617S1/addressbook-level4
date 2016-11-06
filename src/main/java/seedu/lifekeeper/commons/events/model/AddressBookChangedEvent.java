package seedu.lifekeeper.commons.events.model;

import seedu.lifekeeper.commons.events.BaseEvent;
import seedu.lifekeeper.model.ReadOnlyLifeKeeper;

/** Indicates the AddressBook in the model has changed*/
public class AddressBookChangedEvent extends BaseEvent {

    public final ReadOnlyLifeKeeper data;

    public AddressBookChangedEvent(ReadOnlyLifeKeeper data){
        this.data = data;
    }

    @Override
    public String toString() {
        return "number of persons " + data.getPersonList().size() + ", number of tags " + data.getTagList().size();
    }
}
