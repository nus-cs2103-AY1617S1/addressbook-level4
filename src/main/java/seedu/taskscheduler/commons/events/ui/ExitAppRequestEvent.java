package seedu.taskscheduler.commons.events.ui;

import seedu.taskscheduler.commons.events.BaseEvent;

/**
 *
 */
public class ExitAppRequestEvent extends BaseEvent {

    @Override
    public String toString() {
        return this.getClass().getSimpleName();
    }
}
