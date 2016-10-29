package seedu.agendum.commons.events.ui;

import seedu.agendum.commons.events.BaseEvent;
import seedu.agendum.logic.commands.Command;

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
