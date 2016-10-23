package seedu.address.logic.commands;

import java.util.function.Predicate;

import seedu.address.model.task.Task;

/**
 * Shows all tasks in the task manager to the user.
 */
public class ShowDateCommand extends Command {

    public static final String COMMAND_WORD = "showdate";

    public static final String MESSAGE_SUCCESS = "Shown all tasks date";

    public static final String MESSAGE_USAGE = COMMAND_WORD
    		+ ": Shows the list identified by date";
    
    public static String date;
    
    public ShowDateCommand(String date) {
    	this.date = date;
    }

    public static Predicate<Task> filterByDate() {
    	return t -> t.getStartTime().appearOnUIFormatForDate().equals(date);
    }
    
    @Override
    public CommandResult execute() {
        System.out.println(model.getFilteredTaskList().get(3).getStartTime().appearOnUIFormatForDate());
        model.updateFilteredTaskListToShow(filterByDate());
        return new CommandResult(MESSAGE_SUCCESS);
    }
}
