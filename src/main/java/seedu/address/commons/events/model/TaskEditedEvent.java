package seedu.address.commons.events.model;

import seedu.address.commons.events.BaseEvent;
import seedu.address.model.task.ReadOnlyTask;

public class TaskEditedEvent extends BaseEvent {

    private final int index;
    private final ReadOnlyTask task;
    
    public TaskEditedEvent(int index, ReadOnlyTask task){
        this.index = index;
        this.task = task;
    }
    @Override
    public String toString() {
        return null;
    }
    public int getIndex() {
        return index;
    }
    public ReadOnlyTask getAddedTask() {
        return task;
    }
    
}
