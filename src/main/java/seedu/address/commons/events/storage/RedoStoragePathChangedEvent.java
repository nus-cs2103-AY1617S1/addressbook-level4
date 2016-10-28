package seedu.address.commons.events.storage;

import seedu.address.commons.events.BaseEvent;

//@@author A0146123R
/**
 * Indicates should redo the change for the path for the storage file
 */
public class RedoStoragePathChangedEvent extends BaseEvent {
    
    public boolean isToClearOld;
    
    public RedoStoragePathChangedEvent(boolean isToClearOld) {
        this.isToClearOld = isToClearOld;
    }

    @Override
    public String toString() {
        return this.getClass().getSimpleName();
    }

}
