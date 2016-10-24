package seedu.cmdo.commons.events.ui;

import seedu.cmdo.commons.events.BaseEvent;

/**
 * Indicates that the storage file has been modified by the user
 * 
 * @@author A0139661Y
 */
public class StorageFileChangedEvent extends BaseEvent {
	
	private String filePath;
	
	public StorageFileChangedEvent(String filePath) {
		this.filePath = filePath;
	}

    @Override
    public String toString() {
        return this.getClass().getSimpleName();
    }
    
    public String getFilePath() {
    	return filePath;
    }
}
