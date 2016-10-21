package tars.commons.events.ui;

import tars.commons.events.BaseEvent;

/**
 * Indicates a task has been added
 */
public class TaskAddedEvent extends BaseEvent {

    public final int targetIndex;

    public TaskAddedEvent(int targetIndex) {
        this.targetIndex = targetIndex;
    }

    @Override
    public String toString() {
        return this.getClass().getSimpleName();
    }

}
