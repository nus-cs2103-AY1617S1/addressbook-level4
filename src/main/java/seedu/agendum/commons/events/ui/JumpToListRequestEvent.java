package seedu.agendum.commons.events.ui;

import seedu.agendum.commons.events.BaseEvent;
import seedu.agendum.model.task.Task;

//@@author A0148031R
/**
 * Indicates a request to jump to the list of tasks
 */
public class JumpToListRequestEvent extends BaseEvent {

    public final Task targetTask;
    public final boolean hasMultipleTasks;

    public JumpToListRequestEvent(Task task, boolean hasMultipleTasks) {
        this.targetTask = task;
        this.hasMultipleTasks = hasMultipleTasks;
    }

    @Override
    public String toString() {
        return this.getClass().getSimpleName();
    }

}