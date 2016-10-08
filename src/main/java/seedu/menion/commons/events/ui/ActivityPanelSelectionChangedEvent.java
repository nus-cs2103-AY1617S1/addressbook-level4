package seedu.menion.commons.events.ui;

import seedu.menion.commons.events.BaseEvent;
import seedu.menion.model.activity.ReadOnlyActivity;

/**
 * Represents a selection change in the Activity List Panel
 */
public class ActivityPanelSelectionChangedEvent extends BaseEvent {


    private final ReadOnlyActivity newSelection;

    public ActivityPanelSelectionChangedEvent(ReadOnlyActivity newSelection){
        this.newSelection = newSelection;
    }

    @Override
    public String toString() {
        return this.getClass().getSimpleName();
    }

    public ReadOnlyActivity getNewSelection() {
        return newSelection;
    }
}
