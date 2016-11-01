package w15c2.tusk.commons.events.storage;

import w15c2.tusk.commons.events.BaseEvent;

//@@author A0138978E
/**
 * Indicates that the storage location has changed
 */
public class StorageChangedEvent extends BaseEvent {

	String storagePath;
	
    public StorageChangedEvent(String storagePath) {
        this.storagePath = storagePath;
    }
    
    public String getStoragePath() {
    	return storagePath;
    }

    @Override
    public String toString(){
        return storagePath;
    }

}
