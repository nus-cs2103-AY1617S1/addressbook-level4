package seedu.address.commons.events.storage;

import seedu.address.commons.events.BaseEvent;

//@@author A0146123R
/**
 * Indicates the storage path should be changed to a new path.
 */
public class StoragePathChangedEvent extends BaseEvent {

    private String newStorageFilePath;
    private boolean isToClearOld;

    public StoragePathChangedEvent(String newStorageFilePath, boolean isToClearOld) {
        assert newStorageFilePath != null;

        this.newStorageFilePath = newStorageFilePath;
        this.isToClearOld = isToClearOld;
    }

    public String getNewStorageFilePath() {
        return newStorageFilePath;
    }

    public Boolean isToClearOld() {
        return isToClearOld;
    }

    @Override
    public String toString() {
        return this.getClass().getSimpleName();
    }

}