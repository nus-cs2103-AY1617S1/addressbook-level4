package seedu.gtd.logic.commands;


/**
 * Lists all tasks in the address book to the user.
 */

//@@author A0130677A

public class ListCommand extends Command {

    public static final String COMMAND_WORD = "list";
    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Shows a list of all tasks in the task list.";
    public static final String MESSAGE_SUCCESS_LIST = "Listed all undone tasks";
    public static final String MESSAGE_SUCCESS_LIST_DONE = "Listed all tasks done";
    private String arg;
    
    public ListCommand(String arg) {
    	this.arg = arg;
    }
    
    @Override
    public CommandResult execute() {
    	System.out.println("args:" + arg);
    	if (arg.equals(" done")) {
    		System.out.println("in done");
    		model.updateFilteredListToShowRemoved();
    		return new CommandResult(MESSAGE_SUCCESS_LIST_DONE);
    	} else {
    		model.updateFilteredListToShowUndone();
    		return new CommandResult(MESSAGE_SUCCESS_LIST);
    	}
    }
}
