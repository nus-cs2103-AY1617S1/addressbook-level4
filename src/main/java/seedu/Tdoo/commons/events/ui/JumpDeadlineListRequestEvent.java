package seedu.Tdoo.commons.events.ui;

import seedu.Tdoo.commons.events.BaseEvent;
import seedu.Tdoo.model.task.Deadline;
import seedu.Tdoo.model.task.ReadOnlyTask;
import seedu.Tdoo.model.task.Task;

/**
 * Indicates a request to jump to the list of tasks
 */
public class JumpDeadlineListRequestEvent extends BaseEvent {

	public final Deadline task;

	public JumpDeadlineListRequestEvent(ReadOnlyTask task) {
		this.task = (Deadline) task;
	}

	@Override
	public String toString() {
		return this.getClass().getSimpleName();
	}

}
