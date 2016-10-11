package seedu.jimi.commons.events.ui;

import seedu.jimi.commons.events.BaseEvent;
import seedu.jimi.model.task.ReadOnlyTask;

/**
 * Represents a selection change in the FloatingTask List Panel
 */
public class TaskPanelSelectionChangedEvent extends BaseEvent {


    private final ReadOnlyTask newSelection;

    public TaskPanelSelectionChangedEvent(ReadOnlyTask newSelection){
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
