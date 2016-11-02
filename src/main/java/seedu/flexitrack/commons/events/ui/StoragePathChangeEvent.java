package seedu.flexitrack.commons.events.ui;

import seedu.flexitrack.commons.events.BaseEvent;

public class StoragePathChangeEvent extends BaseEvent {
    private final String newPath;
    
    public StoragePathChangeEvent(String newPath) {
        this.newPath = newPath;
    }
    
    @Override
    public String toString() {
        return this.newPath;
    }
}
