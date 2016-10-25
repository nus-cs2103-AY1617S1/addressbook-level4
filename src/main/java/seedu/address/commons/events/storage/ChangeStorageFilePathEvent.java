package seedu.address.commons.events.storage;

import seedu.address.commons.events.BaseEvent;

//@@author A0139498J
/**
 * Indicates a request to change the storage file location
 */
public class ChangeStorageFilePathEvent extends BaseEvent {

    private final String newStorageFilePath;
    
    public ChangeStorageFilePathEvent(String newStorageFilePath) {
        this.newStorageFilePath = newStorageFilePath;
    }
    
    public String getNewStorageFilePath() {
        return newStorageFilePath;
    }
    
    @Override
    public String toString() {
        return this.getClass().getSimpleName();
    }
}
