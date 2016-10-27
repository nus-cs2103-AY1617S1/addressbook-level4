package seedu.todoList.commons.events.storage;

import seedu.todoList.commons.events.BaseEvent;

//@@author A0144061U
/**
 * Indicates to observers when the location of the storage is changed
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
