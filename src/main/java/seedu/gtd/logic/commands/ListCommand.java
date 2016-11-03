package seedu.gtd.logic.commands;


/**
 * Lists all tasks in the address book to the user.
 */
public class ListCommand extends Command {

    public static final String COMMAND_WORD = "list";
    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Shows a list of all tasks in the task list.";
    public static final String MESSAGE_SUCCESS = "Listed all tasks";
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
    	} else {
    		model.updateFilteredListToShowAll();
    	}
        return new CommandResult(MESSAGE_SUCCESS);
    }
}
