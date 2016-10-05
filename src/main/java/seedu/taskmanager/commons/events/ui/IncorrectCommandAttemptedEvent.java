package seedu.taskmanager.commons.events.ui;

import seedu.taskmanager.commons.events.BaseEvent;
import seedu.taskmanager.logic.commands.Command;

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
