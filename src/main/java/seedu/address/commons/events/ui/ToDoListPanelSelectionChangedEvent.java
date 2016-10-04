package seedu.address.commons.events.ui;

import seedu.address.commons.events.BaseEvent;
import seedu.address.model.todo.ReadOnlyToDo;

/**
 * Represents a selection change in the Person List Panel
 */
public class ToDoListPanelSelectionChangedEvent extends BaseEvent {
    private final ReadOnlyToDo selectedToDo;

    public ToDoListPanelSelectionChangedEvent(ReadOnlyToDo selectedToDo){
        this.selectedToDo = selectedToDo;
    }

    @Override
    public String toString() {
        return this.getClass().getSimpleName() + " with selected to-do: " + selectedToDo.toString();
    }

    public ReadOnlyToDo getSelectedToDo() {
        return selectedToDo;
    }
}
