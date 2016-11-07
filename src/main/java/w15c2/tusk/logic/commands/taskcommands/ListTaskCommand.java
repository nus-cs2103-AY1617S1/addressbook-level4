package w15c2.tusk.logic.commands.taskcommands;

import w15c2.tusk.commons.core.EventsCenter;
import w15c2.tusk.commons.events.ui.FilterLabelChangeEvent;
import w15c2.tusk.commons.events.ui.HideHelpRequestEvent;
import w15c2.tusk.commons.events.ui.ShowAliasListEvent;
import w15c2.tusk.commons.events.ui.FilterLabelChangeEvent.COMMANDTYPE;
import w15c2.tusk.commons.exceptions.IllegalValueException;
import w15c2.tusk.logic.commands.CommandResult;

//@@author A0139708W
/**
 * Lists all tasks in the TaskManager to the user.
 */
public class ListTaskCommand extends TaskCommand {
    
    public static final String COMMAND_WORD = "list";
    public static final String ALTERNATE_COMMAND_WORD = null;
    
    public static final String COMMAND_FORMAT = "list\nlist alias\nlist complete[d]";
    public static final String COMMAND_DESCRIPTION = "List Incomplete Tasks\nList Aliases\nList Completed Tasks"; 

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
    
    /**
     * Shows appropriate list based on argument
     * alias -> show alias window
     * complete -> show completed tasks
     * else display incomplete tasks
     */
    @Override
    public CommandResult execute() {
        // Call event to display alias window
        if(argument.equals("alias")) {
            EventsCenter.getInstance().post(new ShowAliasListEvent());
            closeHelpWindow();
            return new CommandResult(MESSAGE_ALIAS_SUCCESS);

        }
        // Display completed tasks
        if(argument.equals("complete") || argument.equals("completed")){
        	model.filterCompletedTasks();
        	if(model.getCurrentFilteredTasks().size() == 0) {
                return new CommandResult(MESSAGE_NO_COMPLETED_TASKS);
            }
        	closeHelpWindow();
        	return new CommandResult(MESSAGE_COMPLETED_SUCCESS);
        }
        //Display incomplete tasks
        else{
            model.clearTasksFilter();
            if(model.getCurrentFilteredTasks().size() == 0) {
                return new CommandResult(MESSAGE_NOTASKS);
            }
            closeHelpWindow();
            return new CommandResult(MESSAGE_SUCCESS);
        }

    }
    
    public String getType(){
    	return argument;
    }

}