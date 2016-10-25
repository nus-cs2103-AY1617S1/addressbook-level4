package seedu.forgetmenot.logic.commands;

import java.util.function.Predicate;

import seedu.forgetmenot.model.task.Task;

/**
 * Shows all tasks in the task manager to the user.
 */
public class ShowDateCommand extends Command {

    public static final String COMMAND_WORD = "showdate";

    public static final String MESSAGE_SUCCESS = "Shown all tasks by date";

    public static final String MESSAGE_USAGE = COMMAND_WORD
    		+ ": Shows the list identified by date";
    
    public static String date;
    
    public ShowDateCommand(String date) {
    	this.date = date;
    }

    public static Predicate<Task> filterByDate() {
    	return t -> (t.getStartTime().appearOnUIFormatForDate().equals(date)
    			|| t.getEndTime().appearOnUIFormatForDate().equals(date));
    }
    
    @Override
    public CommandResult execute() {
    	model.updateFilteredListToShowAll();
        model.updateFilteredTaskListToShow(filterByDate());
        return new CommandResult(MESSAGE_SUCCESS);
    }
}
