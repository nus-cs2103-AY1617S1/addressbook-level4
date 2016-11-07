package seedu.todolist.commons.events.ui;

import seedu.todolist.commons.events.BaseEvent;

/**
 * Indicates an attempt to execute an incorrect command
 */
public class IncorrectCommandAttemptedEvent extends BaseEvent {

    @Override
    public String toString() {
        return this.getClass().getSimpleName();
    }

}
