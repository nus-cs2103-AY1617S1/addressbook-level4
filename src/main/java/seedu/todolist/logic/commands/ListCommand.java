package seedu.todolist.logic.commands;

import java.time.DateTimeException;

import seedu.todolist.model.task.TaskDate;

//@@author A0153736B
/**
 * Lists tasks in the to-do list to the user according to the dateFilter provided.
 */
public class ListCommand extends Command {

    public static final String COMMAND_WORD = "list";

    public static final String MESSAGE_ALLTASKS_SUCCESS = "Listed all tasks";
    public static final String MESSAGE_FILTER_SUCCESS = "Listed filtered tasks";
    public static final String MESSAGE_FILTER_INVALID = "Filter provided is invalid!";
    
    public static final String FILTER_TODAY = "today";
    public static final String FILTER_WEEK = "week";
    public static final String FILTER_MONTH = "month";
    public static final String VALID_FILTER = FILTER_TODAY + "|" + FILTER_WEEK + "|" + FILTER_MONTH;
    
    public static final String MESSAGE_USAGE = COMMAND_WORD + ":\n" 
            + "Displays the list of tasks corresponding to the filter provided.\n"
    		+ "Parameter: [FILTER]\n"
            + "Example: " + COMMAND_WORD + " " + FILTER_TODAY;
    
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
    	if (!dateFilter.matches(VALID_FILTER) && !isValidDate(dateFilter)) {
            return new CommandResult(MESSAGE_FILTER_INVALID);
    	}
    	
    	assert model != null;
		try {
			model.updateFilteredTaskList(dateFilter);
		} catch (DateTimeException dte) {
			return new CommandResult(TaskDate.MESSAGE_DATE_INVALID);
		}
        return new CommandResult(MESSAGE_FILTER_SUCCESS);
    }
    
    /**
     * Returns true if the provided String is a valid date
     */
    private boolean isValidDate(String test) {
        return (test.matches(TaskDate.DATE_VALIDATION_REGEX_FORMAT));
    }
}
