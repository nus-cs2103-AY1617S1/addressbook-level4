package seedu.tasklist.commons.events.model;

import seedu.tasklist.commons.events.BaseEvent;
import seedu.tasklist.model.task.ReadOnlyTask;

public class TaskModifiedEvent extends BaseEvent {

	public final ReadOnlyTask task;
	
	public TaskModifiedEvent(ReadOnlyTask task){
		this.task = task;
	}
	
	@Override
	public String toString() {
		return "Task modified: "+task.toString();
	}

}
