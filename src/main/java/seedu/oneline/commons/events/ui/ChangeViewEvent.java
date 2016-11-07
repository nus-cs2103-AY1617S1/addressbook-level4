package seedu.oneline.commons.events.ui;

import seedu.oneline.commons.events.BaseEvent;

//@@author A0142605N 
/**
 * An event requesting to change view of tasks
 */
public class ChangeViewEvent extends BaseEvent {
    
    private final String newView; 
    
    public ChangeViewEvent(String newView) {
        this.newView = newView; 
    }
    
    @Override
    public String toString() {
        return this.getClass().getSimpleName();
    }

    public String getNewView() {
        return newView; 
    }
}
