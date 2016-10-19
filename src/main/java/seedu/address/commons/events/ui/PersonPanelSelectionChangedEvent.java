package seedu.address.commons.events.ui;

import seedu.address.commons.events.BaseEvent;
import seedu.address.model.activity.ReadOnlyActivity;

/**
 * Represents a selection change in the Person List Panel
 */
public class PersonPanelSelectionChangedEvent extends BaseEvent {


    private final ReadOnlyActivity newSelection;

    public PersonPanelSelectionChangedEvent(ReadOnlyActivity newSelection){
        this.newSelection = newSelection;
    }

    @Override
    public String toString() {
        return this.getClass().getSimpleName();
    }

    public ReadOnlyActivity getNewSelection() {
        return newSelection;
    }
}
