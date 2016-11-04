package seedu.address.commons.events.ui;

import seedu.address.commons.events.BaseEvent;

/**
 * Indicates to the UI to bring out the save window.
 */
public class SaveEvent extends BaseEvent {
    
    public final String saveDirectory;
    
    public SaveEvent(String saveDirectory) {
        this.saveDirectory = saveDirectory;
    }
    
    @Override
    public String toString() {
        return this.getClass().getSimpleName();
    }
}
