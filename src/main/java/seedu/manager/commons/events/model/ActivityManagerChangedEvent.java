package seedu.manager.commons.events.model;

import seedu.manager.commons.events.BaseEvent;
import seedu.manager.model.ReadOnlyActivityManager;

/** Indicates the ActivityManager in the model has changed*/
public class ActivityManagerChangedEvent extends BaseEvent {

    public final ReadOnlyActivityManager data;

    public ActivityManagerChangedEvent(ReadOnlyActivityManager data){
        this.data = data;
    }

    @Override
    public String toString() {
        return "number of activities " + data.getListActivity().size() + ", number of tags " + data.getTagList().size();
    }
}
