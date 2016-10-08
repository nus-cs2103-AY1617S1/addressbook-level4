package seedu.unburden.commons.events.ui;

import seedu.unburden.commons.events.BaseEvent;
import seedu.unburden.model.person.ReadOnlyPerson;

/**
 * Represents a selection change in the Task List Panel
 */
public class PersonPanelSelectionChangedEvent extends BaseEvent {


    private final ReadOnlyPerson newSelection;

    public PersonPanelSelectionChangedEvent(ReadOnlyPerson newSelection){
        this.newSelection = newSelection;
    }

    @Override
    public String toString() {
        return this.getClass().getSimpleName();
    }

    public ReadOnlyPerson getNewSelection() {
        return newSelection;
    }
}
