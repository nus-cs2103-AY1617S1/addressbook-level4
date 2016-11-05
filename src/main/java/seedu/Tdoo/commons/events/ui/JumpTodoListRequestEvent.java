package seedu.Tdoo.commons.events.ui;

import seedu.Tdoo.commons.events.BaseEvent;
import seedu.Tdoo.model.task.ReadOnlyTask;
import seedu.Tdoo.model.task.Task;
import seedu.Tdoo.model.task.Todo;

/**
 * Indicates a request to jump to the list of tasks
 */
public class JumpTodoListRequestEvent extends BaseEvent {

	public final Todo task;

	public JumpTodoListRequestEvent(ReadOnlyTask task) {
		this.task = (Todo) task;
	}

	@Override
	public String toString() {
		return this.getClass().getSimpleName();
	}

}
