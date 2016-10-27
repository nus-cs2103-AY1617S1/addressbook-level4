package seedu.todolist.logic.commands;

import java.time.DateTimeException;

import seedu.todolist.model.task.TaskDate;

/**
 * Lists all tasks in the to-do list to the user.
 */
public class ListCommand extends Command {

    public static final String COMMAND_WORD = "list";
    public static final String MESSAGE_USAGE = COMMAND_WORD + ":\n" 
            + "Displays the list of tasks corresponding to the filter provided.\n"
            + "Example: " + COMMAND_WORD + " today";
    public static final String MESSAGE_ALLTASKS_SUCCESS = "Listed all tasks";
    public static final String MESSAGE_FILTER_SUCCESS = "Listed filtered tasks";
    public static final String MESSAGE_FILTER_INVALID = "Filter provided is invalid!";
    
    private final String dateFilter;
    
    public ListCommand(String dateFilter) {
    	this.dateFilter = dateFilter;
    }

    @Override
    public CommandResult execute() {
    	if (dateFilter.isEmpty()) {
    		model.updateFilteredListToShowAll();
            return new CommandResult(MESSAGE_ALLTASKS_SUCCESS);
    	}
    	else if (!(dateFilter.equals("today") || dateFilter.equals("week") || dateFilter.equals("month"))) {
            if (!isValidDate(dateFilter)) {
                return new CommandResult(MESSAGE_FILTER_INVALID);
            }
    	}
    	
		try {
			model.updateFilteredTaskList(dateFilter);
		} catch (DateTimeException dte) {
			return new CommandResult(TaskDate.MESSAGE_DATE_INVALID);
		}
        return new CommandResult(MESSAGE_FILTER_SUCCESS);
    }
    
    private boolean isValidDate(String test) {
        if (test.matches(TaskDate.DATE_VALIDATION_REGEX_FORMAT)) {
            return true;
        }
        return false;
    }
}
