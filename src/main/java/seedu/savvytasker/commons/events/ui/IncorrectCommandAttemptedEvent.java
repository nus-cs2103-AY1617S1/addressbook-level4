package seedu.savvytasker.commons.events.ui;

import seedu.savvytasker.commons.events.BaseEvent;
import seedu.savvytasker.logic.commands.Command;

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
