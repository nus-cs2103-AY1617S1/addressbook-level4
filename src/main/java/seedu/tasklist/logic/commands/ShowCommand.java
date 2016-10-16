package seedu.tasklist.logic.commands;

import java.util.Date;

import seedu.tasklist.logic.parser.TimePreparser;

/**
 * Shows all tasks that fulfill the category keyword.
 * Keyword matching is case insensitive.
 */
public class ShowCommand extends Command {

    public static final String COMMAND_WORD = "show";

    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Shows all tasks under the requested category. "
            + "The specified keywords (case-insensitive) are shown as a list with index numbers.\n"
            + "Parameters: KEYWORD (all, incomplete, complete, p/[PRIORITY]\n"
            + "Example: " + COMMAND_WORD + " all";

    public static final String MESSAGE_SHOW_FAILURE = "Invalid category. Available categories: all, incomplete, complete, p/[PRIORITY]";
    public static final String MESSAGE_SUCCESS = "Shown requested tasks.";
    private final String keyword;
    
    public ShowCommand(String keyword) {
        this.keyword = keyword;
    }

    @Override
    public CommandResult execute() {
        String requestedTime = TimePreparser.preparse(keyword);
        
    	if (keyword.equals("all")) {
    	    model.updateFilteredListToShowAll();
    	}
    	else if (keyword.equals("incomplete")) {
            model.updateFilteredListToShowIncomplete();
    	}
    	else if (keyword.equals("complete")) {
            model.updateFilteredListToShowComplete();
    	}
    	else if (keyword.equals("p/high") || keyword.equals("p/med") || keyword.equals("p/low")) {
            model.updateFilteredListToShowPriority(keyword);
    	}
    	else if (!requestedTime.isEmpty() && !requestedTime.equals(new Date(0).toString())) {
    	    model.updateFilteredListToShowDate(requestedTime);
    	}
    	else {
            return new CommandResult(String.format(MESSAGE_SHOW_FAILURE));
    	}
    	
        return new CommandResult(String.format(getMessageForTaskListShownSummary(model.getFilteredTaskList().size())));
    }
}