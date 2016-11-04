package seedu.stask.commons.events.ui;

import seedu.stask.commons.events.BaseEvent;
import seedu.stask.model.task.ReadOnlyTask;

/**
 * Represents a selection change in the Undated List Panel
 */
public class UndatedPanelSelectionChangedEvent extends BaseEvent {


    private final ReadOnlyTask newSelection;

    public UndatedPanelSelectionChangedEvent(ReadOnlyTask newSelection){
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
