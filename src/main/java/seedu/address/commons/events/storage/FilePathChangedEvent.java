package seedu.address.commons.events.storage;

import seedu.address.commons.events.BaseEvent;

/**
 *
 */
public class FilePathChangedEvent extends BaseEvent {

    private final String path;
    
    public FilePathChangedEvent(String path) {
        this.path = path;
    }
    
    @Override
    public String toString() {
        return this.path;
    }
}
