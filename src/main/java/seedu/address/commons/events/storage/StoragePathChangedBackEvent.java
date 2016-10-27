package seedu.address.commons.events.storage;

import seedu.address.commons.events.BaseEvent;

/**
 * Indicates the path for the storage file should be changed back
 */
public class StoragePathChangedBackEvent extends BaseEvent {
    
    public boolean isToClearNew;
    
    public StoragePathChangedBackEvent(boolean isToClearNew) {
        this.isToClearNew = isToClearNew;
    }
    
    @Override
    public String toString() {
        return this.getClass().getSimpleName();
    }

}
