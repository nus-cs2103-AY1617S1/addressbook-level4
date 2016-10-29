package seedu.address.model.state;

import seedu.address.model.ReadOnlyTaskManager;
import seedu.address.model.TaskManager;

/**
 * Represents the state of the command list given by the task manager
 */

public class TaskCommandState {
	
	private final ReadOnlyTaskManager taskCommand;
	private final String command;
	
	public TaskCommandState(ReadOnlyTaskManager taskCommand, String command) {
		this.taskCommand = new TaskManager(taskCommand);
		this.command = command;
	}
	
	public ReadOnlyTaskManager getTaskCommand() {
		return taskCommand;
	}
	
	public String getCommand() {
		return command;
	}
	
}