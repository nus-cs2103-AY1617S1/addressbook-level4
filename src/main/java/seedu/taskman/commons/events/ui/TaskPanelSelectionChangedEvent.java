package seedu.taskman.commons.events.ui;

import seedu.taskman.commons.events.BaseEvent;
import seedu.taskman.model.task.EventInterface;

/**
 * Represents a selection change in the Task List Panel
 */
public class TaskPanelSelectionChangedEvent extends BaseEvent {


    private final EventInterface newSelection;

    public TaskPanelSelectionChangedEvent(EventInterface newSelection){
        this.newSelection = newSelection;
    }

    @Override
    public String toString() {
        return this.getClass().getSimpleName();
    }

    public EventInterface getNewSelection() {
        return newSelection;
    }
}
