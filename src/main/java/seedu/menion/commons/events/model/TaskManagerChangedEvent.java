package seedu.menion.commons.events.model;

import seedu.menion.commons.events.BaseEvent;
import seedu.menion.model.ReadOnlyTaskManager;

/** Indicates the AddressBook in the model has changed*/
public class TaskManagerChangedEvent extends BaseEvent {

    public final ReadOnlyTaskManager data;

    public TaskManagerChangedEvent(ReadOnlyTaskManager data){
        this.data = data;
    }

    @Override
    public String toString() {
        return "number of tasks " + data.getTaskList().size() + ", number of tags " + data.getTagList().size();
    }
}
