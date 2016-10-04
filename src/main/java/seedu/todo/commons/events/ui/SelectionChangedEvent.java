package seedu.todo.commons.events.ui;

import seedu.todo.commons.events.BaseEvent;
import seedu.todo.model.person.ReadOnlyPerson;

/**
 * Represents a selection change in the To-do List Panel
 */
public class SelectionChangedEvent extends BaseEvent {

    private final ReadOnlyPerson newSelection;

    public SelectionChangedEvent(ReadOnlyPerson newSelection){
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
