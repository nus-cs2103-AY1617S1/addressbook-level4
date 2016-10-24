package seedu.malitio.commons.events.ui;

import seedu.malitio.commons.events.BaseEvent;
import seedu.malitio.model.task.ReadOnlyDeadline;
import seedu.malitio.model.task.ReadOnlyEvent;
import seedu.malitio.model.task.ReadOnlyFloatingTask;

/**
 * Represents a selection change in the Task List Panel
 */
public class TaskPanelSelectionChangedEvent extends BaseEvent {


    private ReadOnlyFloatingTask newTaskSelection;
    private ReadOnlyDeadline newDeadlineSelection;
    private ReadOnlyEvent newEventSelection;

    public TaskPanelSelectionChangedEvent(ReadOnlyFloatingTask newSelection) {
        this.newTaskSelection = newSelection;
    }
    
    public TaskPanelSelectionChangedEvent(ReadOnlyDeadline newSelection) {
        this.newDeadlineSelection = newSelection;
    }
    
    public TaskPanelSelectionChangedEvent(ReadOnlyEvent newSelection) {
        this.newEventSelection = newSelection;
    }

    @Override
    public String toString() {
        return this.getClass().getSimpleName();
    }

    public ReadOnlyFloatingTask getNewFloatingTaskSelection() {
        return newTaskSelection;
    }
    
    public ReadOnlyDeadline getNewDeadlineSelection() {
        return newDeadlineSelection;
    }
    
    public ReadOnlyEvent getNewEventSelection() {
        return newEventSelection;
    }
}
