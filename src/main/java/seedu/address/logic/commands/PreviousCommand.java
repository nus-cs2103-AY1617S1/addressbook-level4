package seedu.address.logic.commands;

import seedu.address.model.task.Task;
/** 
 * Carries information of previous command: Command word and task.
 */
public class PreviousCommand {

	public String COMMAND_WORD;
	public Task Task;
	
	public PreviousCommand(String command, Task task)
	{
		COMMAND_WORD = command;
		Task = task;	
	}
	
	public String getCommand()
	{
		return COMMAND_WORD;
	}
	
	public Task getTask()
	{
		return Task;
	}
}
