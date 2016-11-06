package seedu.task.commons.events.ui;

import seedu.task.commons.events.BaseEvent;
import seedu.task.model.item.ReadOnlyTask;

//@@author A0144702N
/**
 * Indicates a request to jump to the list of items
 */
public class JumpToTaskListRequestEvent extends BaseEvent {

    public final int targetIndex;
    public final ReadOnlyTask targetTask;

    public JumpToTaskListRequestEvent(ReadOnlyTask task, int targetIndex) {
        this.targetIndex = targetIndex;
        this.targetTask = task;
    }

    @Override
    public String toString() {
        return this.getClass().getSimpleName();
    }

}
