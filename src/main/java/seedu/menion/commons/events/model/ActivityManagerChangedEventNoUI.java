package seedu.menion.commons.events.model;

import seedu.menion.commons.events.BaseEvent;
import seedu.menion.model.ReadOnlyActivityManager;

/**
 * This method is exactly the same as ActivityManagerChangedEvent. Except that this event handler does not 
 * involve the UI.
 */
/** Indicates the Activity Manager in the model has changed*/
public class ActivityManagerChangedEventNoUI extends BaseEvent {

    public final ReadOnlyActivityManager data;

    public ActivityManagerChangedEventNoUI(ReadOnlyActivityManager data){
        this.data = data;
    }

    @Override
    public String toString() {
        return "number of activities " + data.getTaskList().size();
    }
}
