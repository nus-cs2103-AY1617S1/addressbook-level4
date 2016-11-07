package seedu.taskmanager.commons.events.storage;

import seedu.taskmanager.commons.events.BaseEvent;

//@@author A0143641M
/** Indicates the save location of TaskManager in the model has changed*/
public class SaveLocationChangedEvent extends BaseEvent {

    public final String oldLocation;
    public final String newLocation;

    public SaveLocationChangedEvent(String oldLocation, String newLocation){
        this.oldLocation = oldLocation;
        this.newLocation = newLocation;
    }

    @Override
    public String toString() {
        return "Data saved in location: " + newLocation;
    }
}
