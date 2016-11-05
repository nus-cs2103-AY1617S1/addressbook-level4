package seedu.malitio.commons.events.ui;

import seedu.malitio.commons.events.BaseEvent;
import seedu.malitio.model.task.ReadOnlyFloatingTask;

//@@author A0129595N
/**
 * Represents a selection change in the FloatingTask List Panel
 */
public class TaskPanelSelectionChangedEvent extends BaseEvent {


    private ReadOnlyFloatingTask newTaskSelection;

    public TaskPanelSelectionChangedEvent(ReadOnlyFloatingTask newSelection) {
        this.newTaskSelection = newSelection;
    }

    @Override
    public String toString() {
        return this.getClass().getSimpleName();
    }

    public ReadOnlyFloatingTask getNewFloatingTaskSelection() {
        return newTaskSelection;
    }

}
