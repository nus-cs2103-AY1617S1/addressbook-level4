package seedu.address.logic.commands;

import java.util.function.Predicate;

import seedu.address.model.task.Task;

/**
 * Shows all tasks in the task manager to the user.
 */
public class ShowCommand extends Command {

    public static final String COMMAND_WORD = "show";

    public static final String MESSAGE_SUCCESS = "Shown all undone tasks";

    public ShowCommand() {}

    public static Predicate<Task> isNotDone() {
    	return t -> t.getDone() == false;
    }
    
    @Override
    public CommandResult execute() {
        model.updateFilteredTaskListToShow(isNotDone());
        return new CommandResult(MESSAGE_SUCCESS);
    }
}
