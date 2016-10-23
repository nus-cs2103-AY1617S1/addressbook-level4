package seedu.taskscheduler.commons.events.ui;

import seedu.taskscheduler.commons.events.BaseEvent;
import seedu.taskscheduler.logic.commands.Command;

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
