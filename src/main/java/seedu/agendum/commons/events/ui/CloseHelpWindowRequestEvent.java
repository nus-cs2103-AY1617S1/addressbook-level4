package seedu.agendum.commons.events.ui;

import seedu.agendum.commons.events.BaseEvent;

//@@author A0148031R
/**
 * An event that requests to close help window
 */
public class CloseHelpWindowRequestEvent extends BaseEvent{

    @Override
    public String toString() {
        return this.getClass().getSimpleName();
    }

}
