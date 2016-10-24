package seedu.address.logic.commands;

import java.util.function.Predicate;

import seedu.address.model.task.Task;

/**
 * Shows all tasks in the task manager to the user.
 */
public class ShowCommand extends Command {

    public static final String COMMAND_WORD = "show";

    public static final String MESSAGE_SUCCESS = "Shown all undone tasks";

    public static final String MESSAGE_USAGE = COMMAND_WORD
    		+ ": Shows the list identified by keywords";
    
    public ShowCommand() {}

    public static Predicate<Task> isNotDone() {
    	return t -> t.getDone().value == false;
    }
    
    @Override
    public CommandResult execute() {
    	model.updateFilteredListToShowAll();
        model.updateFilteredTaskListToShow(isNotDone());
        return new CommandResult(MESSAGE_SUCCESS);
    }
}
