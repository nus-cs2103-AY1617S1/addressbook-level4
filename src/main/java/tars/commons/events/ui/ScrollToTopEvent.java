package tars.commons.events.ui;

import tars.commons.events.BaseEvent;

/**
 * Indicate that the task list should scroll to the top
 */
public class ScrollToTopEvent extends BaseEvent {

    public ScrollToTopEvent() {}

    @Override
    public String toString() {
        return this.getClass().getSimpleName();
    }

}
