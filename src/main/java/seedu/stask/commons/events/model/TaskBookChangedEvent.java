package seedu.stask.commons.events.model;

import seedu.stask.commons.events.BaseEvent;
import seedu.stask.model.ReadOnlyTaskBook;

/** Indicates the TaskBook in the model has changed*/
public class TaskBookChangedEvent extends BaseEvent {

    public final ReadOnlyTaskBook data;

    public TaskBookChangedEvent(ReadOnlyTaskBook data){
        this.data = data;
    }

    @Override
    public String toString() {
        return "number of tasks " + (data.getDatedTaskList().size() + data.getUndatedTaskList().size()) + ", number of tags " + data.getTagList().size();
    }
}
