package seedu.address.commons.events.ui;

import seedu.address.commons.events.BaseEvent;
//@@author A0139708W
/**
 * An event requesting to hide the help overlay.
 */
public class HideHelpRequestEvent extends BaseEvent {

    @Override
    public String toString() {
        return this.getClass().getSimpleName();
    }

}
