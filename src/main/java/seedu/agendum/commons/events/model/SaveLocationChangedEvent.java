package seedu.agendum.commons.events.model;

import seedu.agendum.commons.events.BaseEvent;

/** Indicates the ToDoList in the model has changed*/
public class SaveLocationChangedEvent extends BaseEvent {

    public final String saveLocation;

    public SaveLocationChangedEvent(String saveLocation){
        this.saveLocation = saveLocation;
    }

    @Override
    public String toString() {
        return "save location: " + saveLocation;
    }
}
