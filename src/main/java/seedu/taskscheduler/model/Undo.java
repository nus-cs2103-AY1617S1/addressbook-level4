package seedu.taskscheduler.model;

import java.util.ArrayList;

import seedu.taskscheduler.model.task.Task;

/**
 * Represents the undo model.
 */

public class Undo {
	
	private final ArrayList<Task> taskList = new ArrayList<Task>();
	private final int index;
	private final String commandKey;
	

    public Undo(String commandKey) {
        this(commandKey,0);
    }
	
	public Undo(String commandKey, int index, Task... tasks) {
		this.commandKey = commandKey;
		this.index = index;
		for (Task task : tasks) {
		    taskList.add(task);
		}
	}
	
	public Task getTask() {
	    if (taskList.size() > 0) {
            return taskList.get(0);
	    }
	    else {
            return null;
	    }
	}

    public Task[] getTaskArray() {
        return taskList.toArray(new Task[taskList.size()]);
    }
    
    public String getArrayString() {
        StringBuilder sb = new StringBuilder();
        for (Task task : taskList) {
            sb.append("\n");
            sb.append(task.toString());
        }
        return sb.toString();
    }
    
    public void addTask(Task task) {
        taskList.add(task);
    }
    
	public int getIndex() {
		return index;
	}
	
	public String getCommandKey() {
		return commandKey;
	}
}
