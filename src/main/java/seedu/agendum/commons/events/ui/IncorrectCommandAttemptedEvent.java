package seedu.agendum.commons.events.ui;

import seedu.agendum.commons.events.BaseEvent;

/**
 * Indicates an attempt to execute an incorrect command
 */
public class IncorrectCommandAttemptedEvent extends BaseEvent {

    public IncorrectCommandAttemptedEvent() {}

    @Override
    public String toString() {
        return this.getClass().getSimpleName();
    }

}
