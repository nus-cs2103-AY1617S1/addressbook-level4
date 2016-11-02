package seedu.jimi.commons.events.ui;

import seedu.jimi.commons.events.BaseEvent;
import seedu.jimi.model.task.ReadOnlyTask;

/**
 * Indicates a request to jump to the list of tasks
 */
public class JumpToListRequestEvent extends BaseEvent {

    public final ReadOnlyTask targetTask;

    public JumpToListRequestEvent(ReadOnlyTask targetTask) {
        this.targetTask = targetTask;
    }

    @Override
    public String toString() {
        return this.getClass().getSimpleName();
    }

}
