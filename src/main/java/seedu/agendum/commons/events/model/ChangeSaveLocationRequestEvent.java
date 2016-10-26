package seedu.agendum.commons.events.model;

import seedu.agendum.commons.events.BaseEvent;
//@@author A0148095X
/** Indicates the ToDoList in the model has changed*/
public class ChangeSaveLocationRequestEvent extends BaseEvent {

    public final String location;

    public ChangeSaveLocationRequestEvent(String saveLocation){
        this.location = saveLocation;
    }

    @Override
    public String toString() {
        return "Request to change save location to: " + location;
    }
}
