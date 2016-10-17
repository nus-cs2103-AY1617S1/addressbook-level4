package teamfour.tasc.commons.events.storage;

import teamfour.tasc.commons.events.BaseEvent;
import teamfour.tasc.model.ReadOnlyTaskList;

/** Indicates the Task List in the model has changed*/
public class TaskListSwitchedEvent extends BaseEvent {

    public final ReadOnlyTaskList data;

    public TaskListSwitchedEvent(ReadOnlyTaskList data){
        this.data = data;
    }

    @Override
    public String toString() {
        return "number of tasks " + data.getTaskList().size() + ", number of tags " + data.getTagList().size();
    }
}
