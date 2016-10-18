package seedu.address.commons.events.model;

import seedu.address.commons.events.BaseEvent;
import seedu.address.model.ReadOnlyTaskList;

/** Indicates the AddressBook in the model has changed*/
public class TaskListChangedEvent extends BaseEvent {

    public final ReadOnlyTaskList data;

    public TaskListChangedEvent(ReadOnlyTaskList data){
        this.data = data;
    }

    @Override
    public String toString() {
        return "number of tasks " + data.getTaskComponentList().size() + ", number of tags " + data.getTagList().size();
    }
}
