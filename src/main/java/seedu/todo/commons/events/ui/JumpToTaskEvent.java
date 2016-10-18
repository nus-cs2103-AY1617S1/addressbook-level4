package seedu.todo.commons.events.ui;

import seedu.todo.commons.events.BaseEvent;

/**
 * Indicates a request to jump to a specific task given an index.
 */
public class JumpToTaskEvent extends BaseEvent {

    public final int targetIndex;

    public JumpToTaskEvent(int targetIndex) {
        this.targetIndex = targetIndex;
    }

    @Override
    public String toString() {
        return this.getClass().getSimpleName();
    }

}
