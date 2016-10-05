package seedu.task.commons.events.ui;

import seedu.task.commons.events.BaseEvent;

/**
 *
 */
public class ExitAppRequestEvent extends BaseEvent {

    @Override
    public String toString() {
        return this.getClass().getSimpleName();
    }
}
