package seedu.menion.commons.events.storage;

import seedu.menion.commons.events.BaseEvent;
import seedu.menion.model.ReadOnlyActivityManager;
import seedu.menion.model.activity.ReadOnlyActivity;
import seedu.menion.storage.XmlActivityManagerStorage;

//@@author A0139515A
/**
 * An event requesting to show pop up message to remind user to restart the application
 */
public class StoragePathChangedEvent extends BaseEvent {

	private final XmlActivityManagerStorage newStorage;
	private final ReadOnlyActivityManager newAM;
	
    public StoragePathChangedEvent(XmlActivityManagerStorage newStorage, ReadOnlyActivityManager newAM){
        this.newStorage = newStorage;
        this.newAM = newAM;
    }
    
    @Override
    public String toString() {
        return this.getClass().getSimpleName();
    }

    public XmlActivityManagerStorage getUpdatedXmlActivityManagerStorage() {
        return newStorage;
    }
    
    public ReadOnlyActivityManager getUpdatedReadOnlyActivityManager() {
        return newAM;
    }
}