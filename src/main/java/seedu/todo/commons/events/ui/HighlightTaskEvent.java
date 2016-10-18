package seedu.todo.commons.events.ui;

import seedu.todo.commons.events.BaseEvent;
import seedu.todo.model.task.ImmutableTask;

/**
 * Request to highlight in the user interface a particular task
 * displayed in the {@link seedu.todo.ui.TodoListPanel}
 */
public class HighlightTaskEvent extends BaseEvent {

    private final ImmutableTask task;

    public HighlightTaskEvent(ImmutableTask task) {
        this.task = task;
    }

    @Override
    public String toString() {
        return this.getClass().getSimpleName();
    }

    public ImmutableTask getTask() {
        return task;
    }
}
