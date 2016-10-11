package seedu.address.logic.commands.taskcommands;

import seedu.address.logic.commands.CommandResult;

/**
 * Lists all tasks in the TaskManager to the user.
 */
public class ListTaskCommand extends TaskCommand {
    
    public static final String COMMAND_WORD = "list";

    public static final String MESSAGE_SUCCESS = "%1$d tasks listed";
    public static final String MESSAGE_NOTASKS = "No tasks to list";

    @Override
    public CommandResult execute() {
        model.clearTasksFilter();
        if(model.getCurrentFilteredTasks().size() == 0) {
            return new CommandResult(MESSAGE_NOTASKS);
        }
        return new CommandResult(String.format(MESSAGE_SUCCESS, model.getCurrentFilteredTasks().size() ));
    }

}