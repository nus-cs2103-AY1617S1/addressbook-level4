package seedu.todo.commons.events.ui;

import seedu.todo.commons.events.BaseEvent;
import seedu.todo.model.task.ImmutableTask;

//@@author A0135805H-reused
/**
 * Represents a selection change in the To-do List Panel of an ImmutableTask.
 */
public class SelectionChangedEvent extends BaseEvent {

    private final ImmutableTask newSelection;

    public SelectionChangedEvent(ImmutableTask newSelection){
        this.newSelection = newSelection;
    }

    @Override
    public String toString() {
        return this.getClass().getSimpleName();
    }

    public ImmutableTask getNewSelection() {
        return newSelection;
    }
}
