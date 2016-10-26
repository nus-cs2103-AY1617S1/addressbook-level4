package seedu.whatnow.commons.events.ui;
//@@author A0139772U
import seedu.whatnow.commons.events.BaseEvent;
import seedu.whatnow.logic.commands.Command;

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
