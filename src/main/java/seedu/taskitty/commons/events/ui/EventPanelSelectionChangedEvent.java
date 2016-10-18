package seedu.taskitty.commons.events.ui;

import seedu.taskitty.commons.events.BaseEvent;
import seedu.taskitty.model.task.ReadOnlyTask;

/**
 * Represents a selection change in the Person List Panel
 */
public class EventPanelSelectionChangedEvent extends BaseEvent {


    private final ReadOnlyTask newSelection;

    public EventPanelSelectionChangedEvent(ReadOnlyTask newSelection){
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
