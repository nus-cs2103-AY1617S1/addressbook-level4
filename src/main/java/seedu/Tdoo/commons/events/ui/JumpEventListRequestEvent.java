package seedu.Tdoo.commons.events.ui;

import seedu.Tdoo.commons.events.BaseEvent;
import seedu.Tdoo.model.task.Event;
import seedu.Tdoo.model.task.ReadOnlyTask;
import seedu.Tdoo.model.task.Task;

/**
 * Indicates a request to jump to the list of tasks
 */
public class JumpEventListRequestEvent extends BaseEvent {

	public final Event task;

	public JumpEventListRequestEvent(ReadOnlyTask task) {
		this.task = (Event) task;
	}

	@Override
	public String toString() {
		return this.getClass().getSimpleName();
	}

}
