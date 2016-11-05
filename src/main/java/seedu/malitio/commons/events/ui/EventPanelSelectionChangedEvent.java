package seedu.malitio.commons.events.ui;

import seedu.malitio.commons.events.BaseEvent;
import seedu.malitio.model.task.ReadOnlyEvent;

//@@author A0129595N
/**
 * Represents a selection change in the Event List Panel
 */
public class EventPanelSelectionChangedEvent extends BaseEvent {
    private ReadOnlyEvent newEventSelection;

    public EventPanelSelectionChangedEvent(ReadOnlyEvent newSelection) {
        this.newEventSelection = newSelection;
    }

    public ReadOnlyEvent getNewEventSelection() {
        return newEventSelection;
    }

    @Override
    public String toString() {
        return this.getClass().getSimpleName();
    }
}
