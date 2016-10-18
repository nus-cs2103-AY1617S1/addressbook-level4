package seedu.menion.commons.events.model;

import seedu.menion.commons.events.BaseEvent;
import seedu.menion.model.ReadOnlyActivityManager;

/** Indicates the Activity Manager in the model has changed*/
public class ActivityManagerChangedEvent extends BaseEvent {

    public final ReadOnlyActivityManager data;

    public ActivityManagerChangedEvent(ReadOnlyActivityManager data){
        this.data = data;
    }

    @Override
    public String toString() {
        return "number of activities " + data.getTaskList().size();
    }
}
