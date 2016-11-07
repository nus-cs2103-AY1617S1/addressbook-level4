package seedu.taskmaster.commons.events.ui;

import seedu.taskmaster.commons.events.BaseEvent;
import seedu.taskmaster.logic.commands.Command;

//@@author A0147967J
/**
 * Indicates an attempt to execute a failed command
 */
public class FailedCommandAttemptedEvent extends BaseEvent {

    public FailedCommandAttemptedEvent(Command command) {
    }

    @Override
    public String toString() {
        return this.getClass().getSimpleName();
    }

}
