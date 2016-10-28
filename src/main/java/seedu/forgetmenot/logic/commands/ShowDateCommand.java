package seedu.forgetmenot.logic.commands;

/**
 * Shows all tasks in the task manager to the user.
 * @@author A0139198N
 */
public class ShowDateCommand extends Command {

    public static final String COMMAND_WORD = "showdate";

    public static final String MESSAGE_SUCCESS = "Shown all tasks by date";

    public static final String MESSAGE_USAGE = COMMAND_WORD
    		+ ": Shows the list identified by date";
    
    public String date;

    
    public ShowDateCommand(String date) {
    	this.date = date;
    }

    @Override
    public CommandResult execute() {
    	model.updateFilteredListToShowAll();
        model.updateFilteredTaskListToShowDate(date);
        return new CommandResult(MESSAGE_SUCCESS);
    }
}
