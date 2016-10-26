package seedu.oneline.commons.events.storage;

import seedu.oneline.commons.events.BaseEvent;

/**
 * Indicates that the storage location has changed
 */
public class StorageLocationChangedEvent extends BaseEvent {

    String storagePath;
    
    public StorageLocationChangedEvent(String storagePath) {
        this.storagePath = storagePath;
    }
    
    public String getStoragePath() {
        return storagePath;
    }

    @Override
    public String toString(){
        return storagePath;
    }

}