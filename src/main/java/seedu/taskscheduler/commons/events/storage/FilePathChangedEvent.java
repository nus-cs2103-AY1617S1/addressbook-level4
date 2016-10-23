package seedu.taskscheduler.commons.events.storage;

import seedu.taskscheduler.commons.events.BaseEvent;

/**
 * Indicates an execution of a user specified-file saving path
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
