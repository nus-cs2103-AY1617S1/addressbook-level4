package seedu.jimi.commons.events.model;

import seedu.jimi.commons.events.BaseEvent;
import seedu.jimi.model.ReadOnlyTaskBook;

/** Indicates the TaskBook in the model has changed*/
public class TaskBookChangedEvent extends BaseEvent {

    public final ReadOnlyTaskBook data;

    public TaskBookChangedEvent(ReadOnlyTaskBook data){
        this.data = data;
    }

    @Override
    public String toString() {
        return "number of tasks " + data.getTaskList().size() + ", number of tags " + data.getTagList().size();
    }
}
