package seedu.taskmanager.commons.events.storage;

import seedu.taskmanager.commons.events.BaseEvent;

/** Indicates the AddressBook in the model has changed*/
public class SaveLocationChangedEvent extends BaseEvent {

    public final String location;

    public SaveLocationChangedEvent(String location){
        this.location = location;
    }

    @Override
    public String toString() {
        return "Data saved in location: " + location;
    }
}
