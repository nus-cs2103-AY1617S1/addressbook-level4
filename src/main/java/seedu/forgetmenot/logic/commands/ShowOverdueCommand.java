package seedu.forgetmenot.logic.commands;

/**
 * Shows all tasks in the task manager to the user.
 */
public class ShowOverdueCommand extends Command {

    public static final String COMMAND_WORD = "show done";

    public static final String MESSAGE_SUCCESS = "Shown all overdue tasks";

    public ShowOverdueCommand() {}
    
    @Override
    public CommandResult execute() {
        model.updateFilteredListToShowAll();
        model.updateFilteredTaskListToShowOverdue();
        return new CommandResult(MESSAGE_SUCCESS);
    }
}
