package seedu.agendum.commons.events.model;

import seedu.agendum.commons.events.BaseEvent;
//@@author A0148095X
/** Indicates a request from model to change the save location of the data file*/
public class ChangeSaveLocationEvent extends BaseEvent {

    public final String location;
    
    private String message;

    public ChangeSaveLocationEvent(String saveLocation){
        this.location = saveLocation;
        this.message = "Request to change save location to: " + location;
    }

    @Override
    public String toString() {
        return message;
    }
}
