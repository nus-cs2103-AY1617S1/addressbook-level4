package seedu.address.commons.events.ui;

import seedu.address.commons.events.BaseEvent;
import seedu.address.model.item.ReadOnlyToDo;

/**
 * Represents a selection change in the Item List Panel
 */
public class PersonPanelSelectionChangedEvent extends BaseEvent {


    private final ReadOnlyToDo newSelection;

    public PersonPanelSelectionChangedEvent(ReadOnlyToDo newSelection){
        this.newSelection = newSelection;
    }

    @Override
    public String toString() {
        return this.getClass().getSimpleName();
    }

    public ReadOnlyToDo getNewSelection() {
        return newSelection;
    }
}
