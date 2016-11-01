package seedu.oneline.commons.events.ui;

import seedu.oneline.commons.events.BaseEvent;

/**
 * An event requesting to view tasks due today
 */
public class ShowDayViewEvent extends BaseEvent {

    @Override
    public String toString() {
        return this.getClass().getSimpleName();
    }

}
