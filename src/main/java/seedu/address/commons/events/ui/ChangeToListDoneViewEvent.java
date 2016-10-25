package seedu.address.commons.events.ui;

import seedu.address.commons.events.BaseEvent;

//@@author A0139498J
/**
 * Indicates a request to view the list of all done tasks
 */
public class ChangeToListDoneViewEvent extends BaseEvent {
    
    @Override
    public String toString() {
        return this.getClass().getSimpleName();
    }
    
}
