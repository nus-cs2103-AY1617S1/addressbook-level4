package seedu.todo.commons.events.ui;

import seedu.todo.commons.events.BaseEvent;

//@@author A0135817B-reused
/**
 * Indicates a request for App termination
 */
public class ExitAppRequestEvent extends BaseEvent {

    @Override
    public String toString() {
        return this.getClass().getSimpleName();
    }
}
