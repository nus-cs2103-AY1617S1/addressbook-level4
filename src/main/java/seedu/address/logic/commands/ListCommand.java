package seedu.address.logic.commands;

//@@author A0142325R
/**
 * Lists tasks or events in the task manager to the user.
 */
public class ListCommand extends Command {

    public static final String COMMAND_WORD = "list";
    
    public static final String MESSAGE_USAGE = COMMAND_WORD
            + ": List tasks and events"
            + "Parameters: [type]\n"
            + "Example: "
            + " list, list events, list tasks, list done, list undone";

    public static final String MESSAGE_SUCCESS = "Listed all tasks and events";
    public static final String MESSAGE_EVENT_SUCCESS="Listed all events";
    public static final String MESSAGE_TASK_SUCCESS="Listed all tasks";
    public static final String MESSAGE_LIST_DONE_TASK_SUCCESS="Listed all done tasks or events";
    public static final String MESSAGE_LIST_UNDONE_TASK_SUCCESS="Listed all undone tasks or events";
    
    public static final String MESSAGE_INVALID_LIST_COMMAND = "The list command argument provided is invalid."
            + MESSAGE_USAGE;
    
    private String toList=null;
    
    public ListCommand() {}
    public ListCommand(String typeToList){
    	toList=typeToList.trim();
    }

    @Override
    public CommandResult execute() {
    	if(toList==null){
        model.updateFilteredListToShowAll();
        return new CommandResult(MESSAGE_SUCCESS);
    	}else if(toList.equals("events")){
    		model.updateFilteredTaskList(toList);
    		return new CommandResult(MESSAGE_EVENT_SUCCESS);
    	}else if (toList.equals("tasks")){
    		model.updateFilteredTaskList(toList);
    		return new CommandResult(MESSAGE_TASK_SUCCESS);
    	}else if (toList.equals("done")) {
    		model.updateFilteredTaskList(toList);
    		return new CommandResult(MESSAGE_LIST_DONE_TASK_SUCCESS);
    	} else if (toList.equals("undone")) {
    	    model.updateFilteredTaskList(toList);
    	    return new CommandResult(MESSAGE_LIST_UNDONE_TASK_SUCCESS); 
    	} else {
    	    return new CommandResult(MESSAGE_INVALID_LIST_COMMAND);
    	}
    }
}
