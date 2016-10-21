package seedu.flexitrack.commons.events.ui;

import seedu.flexitrack.commons.events.BaseEvent;
import seedu.flexitrack.logic.commands.Command;

/**
 * Indicates an attempt to execute an incorrect command
 */
public class IncorrectCommandAttemptedEvent extends BaseEvent {

    public IncorrectCommandAttemptedEvent(Command command) {
    }

    @Override
    public String toString() {
        return this.getClass().getSimpleName();
    }

}
