package seedu.task.logic.commands;


/**
 * Lists all tasks in the task manager to the user.
 */
public class ListCommand extends Command {

    public static final String COMMAND_WORD = "list";
    
    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Lists all tasks according to the modifier given.\n"
            + "Parameters: [-pr] [-st] [-ed]\n"
            + "Example: " + COMMAND_WORD + " -pr";

    public static final String MESSAGE_SUCCESS = "Listed all tasks";
    
    private String modifier;

    public ListCommand(String args) {
        modifier = args;
    }

    @Override
    public CommandResult execute() {
        model.updateFilteredListToShowAll();
        model.sortFilteredTaskList(modifier);
        return new CommandResult(MESSAGE_SUCCESS);
    }
}
