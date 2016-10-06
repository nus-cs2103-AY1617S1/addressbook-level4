package seedu.menion.commons.events.model;

import seedu.menion.commons.events.BaseEvent;
import seedu.menion.model.ReadOnlyTaskManager;

/** Indicates the Activity Manager in the model has changed*/
public class ActivityManagerChangedEvent extends BaseEvent {

    public final ReadOnlyTaskManager data;

    public ActivityManagerChangedEvent(ReadOnlyTaskManager data){
        this.data = data;
    }

    @Override
    public String toString() {
        return "number of activities " + data.getTaskList().size() + ", number of tags " + data.getTagList().size();
    }
}
