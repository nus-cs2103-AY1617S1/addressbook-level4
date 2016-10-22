package seedu.address.logic.commands.taskcommands;

import seedu.address.commons.core.EventsCenter;
import seedu.address.commons.events.ui.ShowAliasListEvent;
import seedu.address.commons.exceptions.IllegalValueException;
import seedu.address.logic.commands.CommandResult;

/**
 * Lists all tasks in the TaskManager to the user.
 */
public class ListTaskCommand extends TaskCommand {
    
    public static final String COMMAND_WORD = "list";
    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Lists all tasks/alias/completed tasks. \n"
            + "1) list\n"
            + "2) list alias\n"
            + "3) list completed/complete\n";
         
    public static final String MESSAGE_SUCCESS = "Listed all tasks";
    public static final String MESSAGE_NOTASKS = "No tasks to list";
    public static final String MESSAGE_ALIAS_SUCCESS = "Listed all aliases";
    public static final String MESSAGE_COMPLETED_SUCCESS = "Listed all completed tasks";
    public static final String MESSAGE_NO_COMPLETED_TASKS = "No completed tasks to list";


    public final String argument;
    
    public ListTaskCommand(String argument) 
    		throws IllegalValueException {

        this.argument = argument;
        if(!argument.equals("") && !argument.equals("alias") && !argument.equals("completed") &&!argument.equals("complete")){
    		throw new IllegalValueException(MESSAGE_USAGE);
        }
    }

    @Override
    public CommandResult execute() {
        if(argument.equals("alias")) {
            EventsCenter.getInstance().post(new ShowAliasListEvent());
            return new CommandResult(MESSAGE_ALIAS_SUCCESS);

        }
        if(argument.equals("complete") || argument.equals("completed")){
        	model.filterCompletedTasks();
        	if(model.getCurrentFilteredTasks().size() == 0) {
                return new CommandResult(MESSAGE_NO_COMPLETED_TASKS);
            }
        	return new CommandResult(MESSAGE_COMPLETED_SUCCESS);
        }
        else{
            model.clearTasksFilter();
            if(model.getCurrentFilteredTasks().size() == 0) {
                return new CommandResult(MESSAGE_NOTASKS);
            }
            return new CommandResult(MESSAGE_SUCCESS);
        }

    }
    
    public String getType(){
    	return argument;
    }

}