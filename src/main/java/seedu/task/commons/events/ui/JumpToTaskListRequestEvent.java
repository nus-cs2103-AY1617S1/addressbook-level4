package seedu.task.commons.events.ui;

import seedu.task.commons.events.BaseEvent;
import seedu.task.model.item.ReadOnlyTask;

/**
 * Indicates a request to jump to the list of items
 */
public class JumpToTaskListRequestEvent extends BaseEvent {

    public final int targetIndex;
    public final ReadOnlyTask targetTask;
    //@@author A0144702N
    public JumpToTaskListRequestEvent(ReadOnlyTask task, int targetIndex) {
        this.targetIndex = targetIndex;
        this.targetTask = task;
    }

    @Override
    public String toString() {
        return this.getClass().getSimpleName();
    }

}
