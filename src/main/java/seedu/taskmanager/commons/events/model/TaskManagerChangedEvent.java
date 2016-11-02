package seedu.taskmanager.commons.events.model;

import seedu.taskmanager.commons.events.BaseEvent;
import seedu.taskmanager.model.ReadOnlyTaskManager;

/** Indicates the TaskManager in the model has changed*/
public class TaskManagerChangedEvent extends BaseEvent {

    public final ReadOnlyTaskManager data;

    public TaskManagerChangedEvent(ReadOnlyTaskManager data){
        this.data = data;
    }

    @Override
    public String toString() {
        return "number of items " + data.getItemList().size() + ", number of tags " + data.getTagList().size();
    }
}
