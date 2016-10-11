package seedu.taskman.commons.events.model;

import seedu.taskman.commons.events.BaseEvent;
import seedu.taskman.model.ReadOnlyTaskMan;

/** Indicates the TaskMan in the model has changed*/
public class TaskManChangedEvent extends BaseEvent {

    public final ReadOnlyTaskMan data;

    public TaskManChangedEvent(ReadOnlyTaskMan data){
        this.data = data;
    }

    @Override
    public String toString() {
        return "number of tasks " + data.getActivityList().size() + ", number of tags " + data.getTagList().size();
    }
}
