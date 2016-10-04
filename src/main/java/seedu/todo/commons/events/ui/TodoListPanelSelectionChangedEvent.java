package seedu.todo.commons.events.ui;

import seedu.todo.commons.events.BaseEvent;
import seedu.todo.model.person.ReadOnlyPerson;

/**
 * Represents a selection change in the Person List Panel
 */
public class TodoListPanelSelectionChangedEvent extends BaseEvent {


    private final ReadOnlyPerson newSelection;

    public TodoListPanelSelectionChangedEvent(ReadOnlyPerson newSelection){
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
