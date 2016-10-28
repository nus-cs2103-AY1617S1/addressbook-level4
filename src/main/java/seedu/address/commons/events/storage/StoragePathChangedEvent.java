package seedu.address.commons.events.storage;

import seedu.address.commons.events.BaseEvent;

//@@author A0146123R
/**
 * Indicates the path for the storage file has changed
 */
public class StoragePathChangedEvent extends BaseEvent {

    public String newStorageFilePath;
    public boolean isToClearOld;
    
    public StoragePathChangedEvent(String newStorageFilePath, boolean isToClearOld) {
        assert newStorageFilePath != null;
        
        this.newStorageFilePath = newStorageFilePath;
        this.isToClearOld = isToClearOld;
    }
    
    @Override
    public String toString() {
        return this.getClass().getSimpleName();
    }
    
}
