package seedu.address.logic.commands;

import java.util.Optional;

import seedu.address.model.task.TaskFilter;

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

    private Optional<String> taskType;
    private Optional<String> doneStatus;
    
    public ListCommand() {}
    
    public ListCommand(String taskType, String doneStatus) {
    	this.taskType = Optional.ofNullable(taskType);
    	this.doneStatus = Optional.ofNullable(doneStatus);
    }

    @Override
    public CommandResult execute() {
    	System.out.println("DEBUG1");
    	//model.updateFilteredListToShowAll();
    	if(taskType.isPresent()) {
    		System.out.println("DEBUG2");
    		assert taskType.get().equals("someday") || 
    				taskType.get().equals("deadline") || 
    				taskType.get().equals("event"); 
    		System.out.println("DEBUG3");
    		switch(taskType.get()) {
    		case "someday":
    			System.out.println("DEBUG4");
    			model.updateFilteredTaskList(TaskFilter.isSomedayTask());
    			break;
    		case "deadline":
    			System.out.println("DEBUG5");
    			model.updateFilteredTaskList(TaskFilter.isDeadlineTask());
    			break;
    		case "event":
    			System.out.println("DEBUG6");
    			model.updateFilteredTaskList(TaskFilter.isEventTask());
    			break;
    		}
    	}
        return new CommandResult(MESSAGE_SUCCESS);
    }
}
