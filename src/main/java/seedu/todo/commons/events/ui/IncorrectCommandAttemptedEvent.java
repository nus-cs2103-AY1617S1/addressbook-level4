package seedu.todo.commons.events.ui;

import seedu.todo.commons.events.BaseEvent;
import seedu.todo.logic.commands.BaseCommand;

/**
 * Indicates an attempt to execute an incorrect command
 */
public class IncorrectCommandAttemptedEvent extends BaseEvent {

    public IncorrectCommandAttemptedEvent(BaseCommand command) {}

    @Override
    public String toString() {
        return this.getClass().getSimpleName();
    }

}
