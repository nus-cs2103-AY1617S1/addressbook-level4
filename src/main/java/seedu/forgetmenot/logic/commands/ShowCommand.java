package seedu.forgetmenot.logic.commands;

/**
 * Shows all tasks in the task manager to the user.
 * @@author A0139198N
 */
public class ShowCommand extends Command {

    public static final String COMMAND_WORD = "show";

    public static final String MESSAGE_SUCCESS_SHOW = "Shown all undone tasks";
    public static final String MESSAGE_SUCCESS_OVERDUE = "Shown all overdue tasks";
    public static final String MESSAGE_SUCCESS_DATE = "Shown all tasks by date";
    public static final String MESSAGE_SUCCESS_ALL = "Shown all tasks";
    public static final String MESSAGE_SUCCESS_DONE = "Shown all done tasks";

    public static final String MESSAGE_USAGE = COMMAND_WORD
    		+ ": Shows the list identified by keywords";
    
    public String command;
    
    public ShowCommand(String command) {
    	this.command = command;
    }

    @Override
    public CommandResult execute() {
    	if (command.equals("")) {
    		model.updateFilteredListToShowAll();
    		model.updateFilteredTaskListToShowNotDone();
    		return new CommandResult(MESSAGE_SUCCESS_SHOW);
    	}
    	
    	if (command.equals("all")) {
    		model.updateFilteredListToShowAll();
            return new CommandResult(MESSAGE_SUCCESS_ALL);
    	}
    	
    	if (command.equals("done")) {
        	model.updateFilteredListToShowAll();
            model.updateFilteredTaskListToShowDone();
            return new CommandResult(MESSAGE_SUCCESS_DONE);
    	}
    	
    	if (command.equals("overdue")) {
            model.updateFilteredListToShowAll();
            model.updateFilteredTaskListToShowOverdue();
            return new CommandResult(MESSAGE_SUCCESS_OVERDUE);
    	}
    	
    	else {
    	    model.updateFilteredListToShowAll();
    	    model.updateFilteredTaskListToShowDate(command);
    	    return new CommandResult(MESSAGE_SUCCESS_DATE);
    	}
    }
}
