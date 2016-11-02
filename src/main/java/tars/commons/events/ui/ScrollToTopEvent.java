package tars.commons.events.ui;

import tars.commons.events.BaseEvent;

/**
 * Indicate UI to scroll to top
 */
public class ScrollToTopEvent extends BaseEvent {

    public ScrollToTopEvent() {}

    @Override
    public String toString() {
        return this.getClass().getSimpleName();
    }

}
