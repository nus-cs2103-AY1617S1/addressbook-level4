package seedu.forgetmenot.commons.events.model;

import seedu.forgetmenot.commons.events.BaseEvent;
import seedu.forgetmenot.model.ReadOnlyTaskManager;

/** Indicates the TaskManager in the model has changed*/
public class TaskManagerChangedEvent extends BaseEvent {

    public final ReadOnlyTaskManager data;

    public TaskManagerChangedEvent(ReadOnlyTaskManager data){
        this.data = data;
    }

    @Override
    public String toString() {
        return "number of tasks " + data.getTaskList().size();
    }
}
