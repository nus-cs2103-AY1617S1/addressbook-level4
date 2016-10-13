package seedu.address.model;

import seedu.address.model.task.Task;

public class Undo {
	
	private final Task task;
	private final int index;
	private final String commandKey;
	
	public Undo(String commandKey, int index, Task task) {
		this.commandKey = commandKey;
		this.index = index;
		this.task = task;
	}
	
	public Task getTask() {
		return task;
	}
	
	public int getIndex() {
		return index;
	}
	
	public String getCommandKey() {
		return commandKey;
	}
}
