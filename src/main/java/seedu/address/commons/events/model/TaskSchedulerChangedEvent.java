package seedu.address.commons.events.model;

import seedu.address.commons.events.BaseEvent;
import seedu.address.model.ReadOnlyTaskScheduler;

/** Indicates the TaskScheduler in the model has changed*/
public class TaskSchedulerChangedEvent extends BaseEvent {

    public final ReadOnlyTaskScheduler data;

    public TaskSchedulerChangedEvent(ReadOnlyTaskScheduler data){
        this.data = data;
    }

    @Override
    public String toString() {
        return "number of tasks " + data.getTaskList().size() + ", number of tags " + data.getTagList().size();
    }
}
