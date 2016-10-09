package seedu.todolist.commons.events.ui;

import seedu.todolist.commons.events.BaseEvent;

/**
 *
 */
public class ExitAppRequestEvent extends BaseEvent {

    @Override
    public String toString() {
        return this.getClass().getSimpleName();
    }
}
