package seedu.address.commons.events.ui;

import javafx.collections.ObservableList;
import seedu.address.commons.events.BaseEvent;
import seedu.address.model.task.ReadOnlyTask;
//@@author A0142184L
public class DisplayTaskListEvent extends BaseEvent {

	public final ObservableList<ReadOnlyTask> list;
	
    public DisplayTaskListEvent (ObservableList<ReadOnlyTask> taskList) {
    	this.list = taskList;
    }
    
	@Override
	public String toString() {
        return this.getClass().getSimpleName();
	}

}
