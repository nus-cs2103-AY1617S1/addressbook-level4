package seedu.forgetmenot.commons.events.ui;

import seedu.forgetmenot.commons.events.BaseEvent;
import seedu.forgetmenot.logic.commands.Command;

/**
 * Indicates an attempt to execute an incorrect command
 */
public class IncorrectCommandAttemptedEvent extends BaseEvent {

    public IncorrectCommandAttemptedEvent(Command command) {}

    @Override
    public String toString() {
        return this.getClass().getSimpleName();
    }

}
