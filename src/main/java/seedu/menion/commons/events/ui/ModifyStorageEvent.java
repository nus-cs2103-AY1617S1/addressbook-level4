package seedu.menion.commons.events.ui;

import seedu.menion.commons.events.BaseEvent;
import seedu.menion.model.activity.ReadOnlyActivity;

//@@author A0139515A
/**
 * An event requesting to show pop up message to remind user to restart the application
 */
public class ModifyStorageEvent extends BaseEvent {

	private final String newFilePath;
	
    public ModifyStorageEvent(String newFilePath){
        this.newFilePath = newFilePath;
    }
    
    @Override
    public String toString() {
        return this.getClass().getSimpleName();
    }

    public String getNewFilePath() {
        return newFilePath;
    }
}
