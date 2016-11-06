package seedu.todo.commons.events.storage;

import seedu.todo.commons.events.BaseEvent;

//@@author A0093896H
/** Indicates the save location for the data has changed*/
public class SaveLocationChangedEvent extends BaseEvent {
   
    public final String location;

    public SaveLocationChangedEvent(String location){
        this.location = location;
    }

    @Override
    public String toString() {
        return this.location;
    }
}
