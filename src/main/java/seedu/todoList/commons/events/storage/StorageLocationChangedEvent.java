package seedu.todoList.commons.events.storage;

import seedu.todoList.commons.events.BaseEvent;

/**
 * Indicates an exception during a file saving
 */
public class StorageLocationChangedEvent extends BaseEvent {

    public String newDirectory;

    public StorageLocationChangedEvent(String directory) {
        this.newDirectory = directory;
    }
    
    public String getNewDirectory() {
    	return newDirectory;
    }

    @Override
    public String toString(){
        return "New Location: " + this.newDirectory;
    }

}
