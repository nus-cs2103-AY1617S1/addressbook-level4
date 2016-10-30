package seedu.menion.commons.events.ui;

import seedu.menion.commons.events.BaseEvent;

//@@author A0139515A
/**
 * An event requesting to show pop up message to remind user to restart the application
 */
public class ModifyStorageEvent extends BaseEvent {

    @Override
    public String toString() {
        return this.getClass().getSimpleName();
    }

}
