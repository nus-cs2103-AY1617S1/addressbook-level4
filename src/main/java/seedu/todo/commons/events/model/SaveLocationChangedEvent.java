package seedu.todo.commons.events.model;

import seedu.todo.commons.events.BaseEvent;

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
