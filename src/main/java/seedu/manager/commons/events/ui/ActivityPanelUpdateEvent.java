package seedu.manager.commons.events.ui;

import seedu.manager.commons.events.BaseEvent;
import seedu.manager.model.activity.Activity;

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
