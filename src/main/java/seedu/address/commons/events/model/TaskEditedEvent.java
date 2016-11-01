package seedu.address.commons.events.model;

import seedu.address.commons.events.BaseEvent;
import seedu.address.model.task.ReadOnlyTask;

//@@author A0135812L
public class TaskEditedEvent extends BaseEvent {

    private final int index;
    private final ReadOnlyTask task;
    
    public TaskEditedEvent(int index, ReadOnlyTask task){
        this.index = index;
        this.task = task;
    }
    @Override
    public String toString() {
        return "Edited and scrolling to" + task.getAsText();
    }
    public int getIndex() {
        return index;
    }
    public ReadOnlyTask getAddedTask() {
        return task;
    }
    
}
//@@author