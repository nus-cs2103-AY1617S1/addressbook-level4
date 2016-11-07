package seedu.toDoList.commons.events.ui;

import seedu.toDoList.commons.events.BaseEvent;
import seedu.toDoList.logic.commands.Command;

/**
 * Indicates an attempt to execute an incorrect command.
 */
public class IncorrectCommandAttemptedEvent extends BaseEvent {

    public IncorrectCommandAttemptedEvent(Command command) {}

    @Override
    public String toString() {
        return this.getClass().getSimpleName();
    }

}
