package seedu.address.logic.commands;


/**
 * Lists all persons in the address book to the user.
 */
public class ListCommand extends Command {

    public static final String COMMAND_WORD = "list";

    public static final String MESSAGE_SUCCESS = "Listed all tasks";
    public static final String MESSAGE_EVENT_SUCCESS="Listed all events";
    public static final String MESSAGE_TASK_SUCCESS="Listed all floating tasks";
    
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
    	}else{
    		model.updateFilteredTaskList(toList);
    		return new CommandResult(MESSAGE_TASK_SUCCESS);
    	}
    }
}
