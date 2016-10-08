package seedu.emeraldo.commons.events.ui;

import seedu.address.model.person.ReadOnlyTask;
import seedu.emeraldo.commons.events.BaseEvent;

/**
 * Represents a selection change in the Person List Panel
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
