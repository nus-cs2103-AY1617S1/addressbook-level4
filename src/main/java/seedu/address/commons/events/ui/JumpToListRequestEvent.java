package seedu.address.commons.events.ui;

import seedu.address.commons.events.BaseEvent;

/**
 * Indicates a request to jump to the list of tasks
 */
public class JumpToListRequestEvent extends BaseEvent {

    public final int targetIndex;
    public final char category;
    
    public JumpToListRequestEvent(int targetIndex, char category) {
    	this.targetIndex = targetIndex;
    	this.category = category;
    }

    @Override
    public String toString() {
        return this.getClass().getSimpleName();
    }

}
