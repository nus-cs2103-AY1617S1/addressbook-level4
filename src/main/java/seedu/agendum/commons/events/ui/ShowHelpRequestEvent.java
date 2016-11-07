package seedu.agendum.commons.events.ui;

import seedu.agendum.commons.events.BaseEvent;

//@@author A0148031R
/**
 * An event requesting to view the help page.
 */
public class ShowHelpRequestEvent extends BaseEvent {

    @Override
    public String toString() {
        return this.getClass().getSimpleName();
    }

}
