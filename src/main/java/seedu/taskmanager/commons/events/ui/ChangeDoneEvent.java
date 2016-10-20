package seedu.taskmanager.commons.events.ui;

import seedu.taskmanager.commons.events.BaseEvent;

/**
 * Indicates a request to jump to the list of persons
 */
public class ChangeDoneEvent extends BaseEvent {

    public ChangeDoneEvent() {
    }

    @Override
    public String toString() {
        return this.getClass().getSimpleName();
    }

}
