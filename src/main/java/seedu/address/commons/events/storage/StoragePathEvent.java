package seedu.address.commons.events.storage;

import seedu.address.commons.events.BaseEvent;

//@@author A0146123R
/**
 * Indicates a change for the storage file path. It could be changed to a new
 * path or undone or redone the change.
 */
public class StoragePathEvent extends BaseEvent {

    private String newStorageFilePath;

    public StoragePathEvent(String newStorageFilePath) {
        assert newStorageFilePath != null;
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