package seedu.flexitrack.commons.events.ui;

import seedu.flexitrack.commons.events.BaseEvent;
import seedu.flexitrack.model.task.ReadOnlyTask;

/**
 * Represents a selection change in the task List Panel
 */
public class TaskPanelSelectionChangedEvent extends BaseEvent {

    private final ReadOnlyTask newSelection;

    public TaskPanelSelectionChangedEvent(ReadOnlyTask newSelection) {
        this.newSelection = newSelection;
    }

    @Override
    public String toString() {
        return this.getClass().getSimpleName();
    }

    public ReadOnlyTask getNewSelection() {
        return newSelection;
    }
}
