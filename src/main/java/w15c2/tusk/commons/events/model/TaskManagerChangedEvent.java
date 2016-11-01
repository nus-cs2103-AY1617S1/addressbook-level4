package w15c2.tusk.commons.events.model;

import w15c2.tusk.commons.collections.UniqueItemCollection;
import w15c2.tusk.commons.events.BaseEvent;
import w15c2.tusk.model.task.Task;

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
