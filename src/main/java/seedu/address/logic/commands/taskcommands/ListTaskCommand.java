package seedu.address.logic.commands.taskcommands;

import seedu.address.logic.commands.CommandResult;

/**
 * Lists all tasks in the TaskManager to the user.
 */
public class ListTaskCommand extends TaskCommand {
    
    public static final String COMMAND_WORD = "list";

    public static final String MESSAGE_SUCCESS = "Listed all tasks";

    @Override
    public CommandResult execute() {
        model.clearTasksFilter();
        return new CommandResult(MESSAGE_SUCCESS);
    }

}