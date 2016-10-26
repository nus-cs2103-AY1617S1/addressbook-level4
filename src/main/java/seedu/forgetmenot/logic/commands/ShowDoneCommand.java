package seedu.forgetmenot.logic.commands;

/**
 * Shows all tasks in the task manager to the user.
 * @@author A0139198N
 */
public class ShowDoneCommand extends Command {

    public static final String COMMAND_WORD = "showdone";

    public static final String MESSAGE_SUCCESS = "Shown all done tasks";

    public ShowDoneCommand() {}

    @Override
    public CommandResult execute() {
    	model.updateFilteredListToShowAll();
        model.updateFilteredTaskListToShowDone();
        return new CommandResult(MESSAGE_SUCCESS);
    }
    
    
}
