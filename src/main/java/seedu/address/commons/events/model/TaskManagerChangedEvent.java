package seedu.address.commons.events.model;

import seedu.address.commons.collections.UniqueItemCollection;
import seedu.address.commons.events.BaseEvent;
import seedu.address.model.task.Task;

/** Indicates the AddressBook in the model has changed*/
public class TaskManagerChangedEvent extends BaseEvent {

    public final UniqueItemCollection<Task> data;

    public TaskManagerChangedEvent(UniqueItemCollection<Task> data){
        this.data = data;
    }

    @Override
    public String toString() {
        return "number of tasks " + data.getInternalList().size();
    }
}
