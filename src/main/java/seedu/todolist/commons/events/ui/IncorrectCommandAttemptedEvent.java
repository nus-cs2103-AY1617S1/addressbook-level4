package seedu.todolist.commons.events.ui;

import seedu.todolist.commons.events.BaseEvent;
import seedu.todolist.logic.commands.Command;

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
