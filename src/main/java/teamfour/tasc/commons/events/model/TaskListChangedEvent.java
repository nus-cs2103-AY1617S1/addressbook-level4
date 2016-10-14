package teamfour.tasc.commons.events.model;

import teamfour.tasc.commons.events.BaseEvent;
import teamfour.tasc.model.ReadOnlyTaskList;

/** Indicates the Task List in the model has changed*/
public class TaskListChangedEvent extends BaseEvent {

    public final ReadOnlyTaskList data;

    public TaskListChangedEvent(ReadOnlyTaskList data){
        this.data = data;
    }

    @Override
    public String toString() {
        return "number of tasks " + data.getTaskList().size() + ", number of tags " + data.getTagList().size();
    }
}
