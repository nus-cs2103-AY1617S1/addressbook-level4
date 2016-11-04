package seedu.simply.commons.events.model;

import seedu.simply.commons.events.BaseEvent;
import seedu.simply.model.ReadOnlyTaskBook;

/** Indicates the AddressBook in the model has changed*/
public class TaskBookChangedEvent extends BaseEvent {

    public final ReadOnlyTaskBook data;

    public TaskBookChangedEvent(ReadOnlyTaskBook data){
        this.data = data;
    }

    @Override
    public String toString() {
        return "number of events " + data.getEventList().size() + ", number of tags " + data.getTagList().size();
    }
}
