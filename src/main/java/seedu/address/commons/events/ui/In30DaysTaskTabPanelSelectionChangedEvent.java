package seedu.address.commons.events.ui;

import seedu.address.commons.events.BaseEvent;
import seedu.address.model.task.ReadOnlyTask;

/**
 * Represents a selection change in the Task List Panel
 */
public class In30DaysTaskTabPanelSelectionChangedEvent extends BaseEvent {


    private final ReadOnlyTask newSelection;

    public In30DaysTaskTabPanelSelectionChangedEvent(ReadOnlyTask newSelection){
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
