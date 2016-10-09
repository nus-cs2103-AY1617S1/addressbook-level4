package seedu.address.commons.events.model;

import seedu.address.commons.events.BaseEvent;
import seedu.address.model.ReadOnlySavvyTasker;

/** Indicates the SavvyTasker in the model has changed*/
public class SavvyTaskerChangedEvent extends BaseEvent {

    public final ReadOnlySavvyTasker data;

    public SavvyTaskerChangedEvent(ReadOnlySavvyTasker data){
        this.data = data;
    }

    @Override
    public String toString() {
        return "number of tasks " + data.getReadOnlyListOfTasks().size();
    }
}
