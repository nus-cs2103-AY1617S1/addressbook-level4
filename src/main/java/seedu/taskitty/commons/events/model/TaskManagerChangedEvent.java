package seedu.taskitty.commons.events.model;

import seedu.taskitty.commons.events.BaseEvent;
import seedu.taskitty.model.ReadOnlyTaskManager;

/** Indicates the TaskManager in the model has changed*/
public class TaskManagerChangedEvent extends BaseEvent {

    public final ReadOnlyTaskManager data;

    public TaskManagerChangedEvent(ReadOnlyTaskManager data){
        this.data = data;
    }

    @Override
    public String toString() {
        return "number of persons " + data.getTaskList().size() + ", number of tags " + data.getTagList().size();
    }
}
