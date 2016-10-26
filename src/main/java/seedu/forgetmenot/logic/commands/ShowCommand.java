package seedu.forgetmenot.logic.commands;

/**
 * Shows all tasks in the task manager to the user.
 */
public class ShowCommand extends Command {

    public static final String COMMAND_WORD = "show";

    public static final String MESSAGE_SUCCESS = "Shown all undone tasks";

    public static final String MESSAGE_USAGE = COMMAND_WORD
    		+ ": Shows the list identified by keywords";
    
    public ShowCommand() {}

    @Override
    public CommandResult execute() {
    	model.updateFilteredListToShowAll();
        model.updateFilteredTaskListToShowNotDone();
        return new CommandResult(MESSAGE_SUCCESS);
    }
}
