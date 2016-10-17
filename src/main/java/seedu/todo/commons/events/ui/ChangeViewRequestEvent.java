package seedu.todo.commons.events.ui;

import seedu.todo.commons.enumerations.TaskViewFilters;
import seedu.todo.commons.events.BaseEvent;

/**
 * Represents a selection change in the To-do List Panel of an ImmutableTask.
 */
public class ChangeViewRequestEvent extends BaseEvent {

    private final TaskViewFilters view;

    public ChangeViewRequestEvent(TaskViewFilters view){
        this.view = view;
    }

    @Override
    public String toString() {
        return this.getClass().getSimpleName();
    }

    public TaskViewFilters getNewSelection() {
        return view;
    }
}
