package seedu.oneline.commons.events.ui;

import seedu.oneline.commons.events.BaseEvent;

/**
 * An event requesting to view list of all tasks without deadlines
 */
public class ShowFloatViewEvent extends BaseEvent {

    @Override
    public String toString() {
        return this.getClass().getSimpleName();
    }

}
