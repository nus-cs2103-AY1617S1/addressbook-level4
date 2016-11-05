package seedu.oneline.commons.events.ui;

import seedu.oneline.commons.events.BaseEvent;

/**
 * An event requesting to view list of all current tasks
 */
public class ShowAllViewEvent extends BaseEvent {

    @Override
    public String toString() {
        return this.getClass().getSimpleName();
    }

}
