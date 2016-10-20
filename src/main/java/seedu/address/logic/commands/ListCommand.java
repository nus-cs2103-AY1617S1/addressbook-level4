package seedu.address.logic.commands;

import seedu.address.model.task.Status;
import seedu.address.model.task.TaskType;

/**
 * Lists all tasks in the address book to the user.
 */
public class ListCommand extends Command {

    public static final String COMMAND_WORD = "list";
    
    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Lists all tasks filtered by specified parameters\n"
            + "Event Parameters: [TASK_TYPE] [done| not-done] [dd-mm-yy] [hh:mm]\n"
            + "Event Example: " + COMMAND_WORD
            + " someday not-done\n";  
    
    public static final String MESSAGE_SUCCESS = "Listed all tasks";
    
    private TaskType taskType;
    private Status status;
    
    public ListCommand() {
    	taskType = null;
    	status = null;
    }
    
    public ListCommand(TaskType taskType, Status status) {
    	this.taskType = taskType;
    	this.status = status;
    }

    @Override
    public CommandResult execute() {
    	model.updateFilteredListToShowAll();
	 return new CommandResult(MESSAGE_SUCCESS);
    }

}
