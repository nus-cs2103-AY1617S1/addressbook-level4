package seedu.taskitty.commons.events.storage;

import seedu.taskitty.commons.events.BaseEvent;

//@@author A0135793W
/**
 * Indicates that storage path location has changed
 */
public class PathLocationChangedEvent extends BaseEvent{
    String pathLocation;
    
    public PathLocationChangedEvent(String pathLocation) {
        this.pathLocation = pathLocation;
    }
    
    public String getPathLocation() {
        return pathLocation;
    }
    
    @Override
    public String toString() {
        return pathLocation;
    }

}
