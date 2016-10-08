package seedu.unburden.commons.events.ui;

import seedu.unburden.commons.events.BaseEvent;

/**
 * Indicates a request for App termination
 */
public class ExitAppRequestEvent extends BaseEvent {

    @Override
    public String toString() {
        return this.getClass().getSimpleName();
    }
}
