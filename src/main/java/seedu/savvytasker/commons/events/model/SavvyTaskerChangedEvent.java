package seedu.savvytasker.commons.events.model;

import seedu.savvytasker.commons.events.BaseEvent;
import seedu.savvytasker.model.ReadOnlySavvyTasker;

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
