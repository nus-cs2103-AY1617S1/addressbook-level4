package seedu.task.commons.events.ui;

import seedu.task.commons.events.BaseEvent;

/**
 * Indicates a request to jump to the list of items
 */
public class JumpToEventListRequestEvent extends BaseEvent {

    public final int targetIndex;

    public JumpToEventListRequestEvent(int targetIndex) {
        this.targetIndex = targetIndex;
    }

    @Override
    public String toString() {
        return this.getClass().getSimpleName();
    }

}
