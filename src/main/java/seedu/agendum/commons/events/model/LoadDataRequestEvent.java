package seedu.agendum.commons.events.model;

import seedu.agendum.commons.events.BaseEvent;

//@@author A0148095X
/** Indicates a request from model to load data **/
public class LoadDataRequestEvent extends BaseEvent {

    public final String loadLocation;

    public LoadDataRequestEvent(String loadLocation){
        this.loadLocation = loadLocation;
    }

    @Override
    public String toString() {
        return "Request to load from: " + loadLocation;
    }
}
