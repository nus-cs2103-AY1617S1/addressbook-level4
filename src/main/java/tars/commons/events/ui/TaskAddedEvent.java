package tars.commons.events.ui;

import tars.commons.events.BaseEvent;
import tars.model.task.ReadOnlyTask;

/**
 * Indicates a task has been added
 */
public class TaskAddedEvent extends BaseEvent {

    public final int targetIndex;
    public final ReadOnlyTask task;

    public TaskAddedEvent(int targetIndex, ReadOnlyTask task) {
        this.targetIndex = targetIndex;
        this.task = task;
    }

    @Override
    public String toString() {
        return this.getClass().getSimpleName();
    }

}
