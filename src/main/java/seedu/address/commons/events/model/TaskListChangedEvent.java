package seedu.address.commons.events.model;

import seedu.address.commons.events.BaseEvent;
import seedu.address.model.ReadOnlyTaskMaster;

/** Indicates the AddressBook in the model has changed*/
public class TaskListChangedEvent extends BaseEvent {

    public final ReadOnlyTaskMaster data;

    public TaskListChangedEvent(ReadOnlyTaskMaster data){
        this.data = data;
    }

    @Override
    public String toString() {
        return "number of tasks " + data.getTaskComponentList().size() + ", number of tags " + data.getTagList().size();
    }
}
