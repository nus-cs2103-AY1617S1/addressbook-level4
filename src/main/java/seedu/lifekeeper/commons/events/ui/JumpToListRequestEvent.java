package seedu.lifekeeper.commons.events.ui;

import seedu.lifekeeper.commons.events.BaseEvent;

/**
 * Indicates a request to jump to the list of activities
 */
public class JumpToListRequestEvent extends BaseEvent {

    public final int targetIndex;

    public JumpToListRequestEvent(int targetIndex) {
        this.targetIndex = targetIndex;
    }

    @Override
    public String toString() {
        return this.getClass().getSimpleName();
    }

}
