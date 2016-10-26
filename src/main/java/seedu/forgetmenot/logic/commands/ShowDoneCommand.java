package seedu.address.logic.commands;

import java.util.function.Predicate;

import seedu.address.model.task.Task;

/**
 * Shows all tasks in the task manager to the user.
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
