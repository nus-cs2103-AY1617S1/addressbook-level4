package seedu.address.commons.events.ui;

import seedu.address.commons.events.BaseEvent;
import seedu.address.model.item.ReadOnlyFloatingTask;
import seedu.address.model.person.ReadOnlyPerson;

/**
 * Represents a selection change in the Person List Panel
 */
public class PersonPanelSelectionChangedEvent extends BaseEvent {


    private final ReadOnlyFloatingTask newSelection;

    public PersonPanelSelectionChangedEvent(ReadOnlyFloatingTask newSelection){
        this.newSelection = newSelection;
    }

    @Override
    public String toString() {
        return this.getClass().getSimpleName();
    }

    public ReadOnlyFloatingTask getNewSelection() {
        return newSelection;
    }
}
