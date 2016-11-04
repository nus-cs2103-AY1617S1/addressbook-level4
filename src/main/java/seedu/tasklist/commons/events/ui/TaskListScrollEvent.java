package seedu.tasklist.commons.events.ui;

import seedu.tasklist.commons.events.BaseEvent;
import seedu.tasklist.ui.TaskListPanel;
import seedu.tasklist.ui.TaskListPanel.Direction;

public class TaskListScrollEvent extends BaseEvent {
	
	public TaskListPanel.Direction dir;

	public TaskListScrollEvent(Direction dir) {
		super();
		this.dir = dir;
	}

	@Override
	public String toString() {
		return "Scroll event of " + dir;
	}

}
