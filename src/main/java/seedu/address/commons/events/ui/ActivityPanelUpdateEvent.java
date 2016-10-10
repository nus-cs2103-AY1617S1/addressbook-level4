package seedu.address.commons.events.ui;

import seedu.address.commons.events.BaseEvent;
import seedu.address.model.activity.Activity;

/**
 * Represents a selection change in the Person List Panel
 */
public class ActivityPanelUpdateEvent extends BaseEvent {


    private final Activity updatedActivity;

    public ActivityPanelUpdateEvent(Activity newValue){
        this.updatedActivity = newValue;
    }

    @Override
    public String toString() {
        return this.getClass().getSimpleName();
    }

    public Activity getNewActivity() {
        return updatedActivity;
    }
}
