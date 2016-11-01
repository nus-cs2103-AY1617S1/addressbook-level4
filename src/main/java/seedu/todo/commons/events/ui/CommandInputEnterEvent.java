package seedu.todo.commons.events.ui;

import seedu.todo.commons.events.BaseEvent;

//@@author A0135805H
/**
 * An event when the user presses "ENTER" on the keyboard in the command input.
 */
public class CommandInputEnterEvent extends BaseEvent {

    @Override
    public String toString() {
        return this.getClass().getSimpleName();
    }
}
