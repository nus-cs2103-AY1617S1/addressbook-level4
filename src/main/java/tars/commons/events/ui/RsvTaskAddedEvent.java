package tars.commons.events.ui;

import tars.commons.events.BaseEvent;
import tars.model.task.rsv.RsvTask;

// @@author A0121533W
/**
 * Indicates a task has been added
 */
public class RsvTaskAddedEvent extends BaseEvent {

    public final int targetIndex;
    public final RsvTask task;

    public RsvTaskAddedEvent(int targetIndex, RsvTask task) {
        this.targetIndex = targetIndex;
        this.task = task;
    }

    @Override
    public String toString() {
        return this.getClass().getSimpleName();
    }

}
