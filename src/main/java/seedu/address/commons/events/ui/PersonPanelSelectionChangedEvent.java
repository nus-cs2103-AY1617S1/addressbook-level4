package seedu.address.commons.events.ui;

import seedu.address.commons.events.BaseEvent;
import seedu.address.model.activity.Activity;
import seedu.address.model.person.ReadOnlyPerson;

/**
 * Represents a selection change in the Person List Panel
 */
public class PersonPanelSelectionChangedEvent extends BaseEvent {


    private final Activity newSelection;

    public PersonPanelSelectionChangedEvent(Activity newValue){
        this.newSelection = newValue;
    }

    @Override
    public String toString() {
        return this.getClass().getSimpleName();
    }

    public Activity getNewSelection() {
        return newSelection;
    }
}
