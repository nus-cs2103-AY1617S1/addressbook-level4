package seedu.unburden.commons.events.ui;

import seedu.unburden.commons.events.BaseEvent;
import seedu.unburden.model.task.ReadOnlyTask;

/**
 * Represents a selection change in the Task List Panel
 */
public class PersonPanelSelectionChangedEvent extends BaseEvent {


    private final ReadOnlyTask newSelection;

    public PersonPanelSelectionChangedEvent(ReadOnlyTask newSelection){
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
