package seedu.taskman.commons.events.ui;

import seedu.taskman.commons.events.BaseEvent;
import seedu.taskman.model.event.Activity;

/**
 * Represents a selection change in the Task List Panel
 */
public class TaskPanelSelectionChangedEvent extends BaseEvent {


    private final Activity newSelection;

    public TaskPanelSelectionChangedEvent(Activity newSelection){
        this.newSelection = newSelection;
    }

    @Override
    public String toString() {
        return this.getClass().getSimpleName();
    }

    public Activity getNewSelection() {
        return newSelection;
    }
}
