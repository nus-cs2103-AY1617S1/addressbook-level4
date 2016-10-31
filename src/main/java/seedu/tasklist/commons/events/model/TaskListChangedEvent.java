package seedu.tasklist.commons.events.model;

import seedu.tasklist.commons.events.BaseEvent;
import seedu.tasklist.model.ReadOnlyTaskList;

/** Indicates the TaskList in the model has changed*/
public class TaskListChangedEvent extends BaseEvent {

    public final ReadOnlyTaskList data;

    public TaskListChangedEvent(ReadOnlyTaskList data){
        this.data = data;
    }

    @Override
    public String toString() {
        return "number of persons " + data.getTaskList().size() + ", number of tags ";
    }
}
