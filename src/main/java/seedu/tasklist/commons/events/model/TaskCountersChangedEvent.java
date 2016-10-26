package seedu.tasklist.commons.events.model;

import seedu.tasklist.commons.events.BaseEvent;
import seedu.tasklist.model.TaskCounter;
//@@author A0146107M
public class TaskCountersChangedEvent extends BaseEvent {

    public final TaskCounter data;

    public TaskCountersChangedEvent(TaskCounter data){
        this.data = data;
    }

    @Override
    public String toString() {
        return "Counters updated!";
    }

}
