package seedu.toDoList.commons.events.storage;

import seedu.toDoList.commons.events.BaseEvent;

//@@author A0146123R
/**
 * Indicates the change for the storage file path should be redone.
 */
public class RedoStoragePathChangedEvent extends BaseEvent {

    @Override
    public String toString() {
        return this.getClass().getSimpleName();
    }

}