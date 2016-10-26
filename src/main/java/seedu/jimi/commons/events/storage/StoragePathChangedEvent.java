package seedu.jimi.commons.events.storage;

import seedu.jimi.commons.events.BaseEvent;

/**
 * 
 * @@author A0143471L
 * 
 * Indicates the event where storage path has changed
 *
 */

public class StoragePathChangedEvent extends BaseEvent {
    
    public final String oldPath;
    public final String newPath;
    
    public StoragePathChangedEvent(String oldPath, String newPath) {
        this.oldPath = oldPath;
        this.newPath = newPath;
    }
    
    @Override
    public String toString() {
        return "Storage Path changed " + newPath;
    }
}
