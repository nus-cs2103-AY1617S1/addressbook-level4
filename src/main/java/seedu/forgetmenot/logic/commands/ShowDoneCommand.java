package seedu.forgetmenot.logic.commands;

import java.util.function.Predicate;

import seedu.forgetmenot.model.task.Task;

/**
 * Shows all tasks in the task manager to the user.
 */
public class ShowDoneCommand extends Command {

    public static final String COMMAND_WORD = "showdone";

    public static final String MESSAGE_SUCCESS = "Shown all done tasks";

    public ShowDoneCommand() {}

    public static Predicate<Task> isDone() {
    	return t -> t.getDone().value == true;
    }
    
    @Override
    public CommandResult execute() {
    	model.updateFilteredListToShowAll();
        model.updateFilteredTaskListToShow(isDone());
        return new CommandResult(MESSAGE_SUCCESS);
    }
    
    
}
