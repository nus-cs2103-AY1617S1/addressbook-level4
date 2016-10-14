package seedu.dailyplanner.commons.events.ui;

import seedu.dailyplanner.commons.events.BaseEvent;
import seedu.dailyplanner.logic.commands.Command;

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
