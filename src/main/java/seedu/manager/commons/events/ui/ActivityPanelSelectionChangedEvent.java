package seedu.manager.commons.events.ui;

import seedu.manager.commons.events.BaseEvent;
import seedu.manager.model.activity.Activity;

/**
 * Represents a selection change in the Person List Panel
 */
public class ActivityPanelSelectionChangedEvent extends BaseEvent {


    private final Activity newSelection;

    public ActivityPanelSelectionChangedEvent(Activity newValue){
        this.newSelection = newValue;
    }

    @Override
    public String toString() {
        return this.getClass().getSimpleName();
    }

    public Activity getNewSelection() {
        return newSelection;
    }
}
