package seedu.todo.commons.events.ui;

import seedu.todo.commons.core.TaskViewFilter;
import seedu.todo.commons.events.BaseEvent;

/**
 * Represents a selection change in the To-do List Panel of an ImmutableTask.
 */
public class ChangeViewRequestEvent extends BaseEvent {

    private final TaskViewFilter view;

    public ChangeViewRequestEvent(TaskViewFilter view){
        this.view = view;
    }

    @Override
    public String toString() {
        return this.getClass().getSimpleName();
    }

    public TaskViewFilter getNewView() {
        return view;
    }
}
