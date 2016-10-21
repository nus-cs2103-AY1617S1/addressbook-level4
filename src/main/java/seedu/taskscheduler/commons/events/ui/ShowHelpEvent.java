package seedu.taskscheduler.commons.events.ui;

import seedu.taskscheduler.commons.events.BaseEvent;

/**
 * An event requesting to view the help page.
 */
public class ShowHelpEvent extends BaseEvent {

    @Override
    public String toString() {
        return this.getClass().getSimpleName();
    }

}
