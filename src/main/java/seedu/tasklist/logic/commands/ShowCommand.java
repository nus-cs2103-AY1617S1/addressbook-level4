package seedu.tasklist.logic.commands;

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
    	switch (keyword) {
    	
        case "all":
    	    model.updateFilteredListToShowAll(); break;
    	    
        case "incomplete":
            model.updateFilteredListToShowIncomplete(); break;
    		
        case "complete":
            model.updateFilteredListToShowComplete(); break;
    		
        case "p/high": case "p/med": case "p/low":
            model.updateFilteredListToShowPriority(keyword); break;
            
        case "floating":
        	model.updateFilteredListToShowFloating(); break;
        	
        case "overdue":
        	model.updateFilteredListToShowOverDue(); break;
        	
        default:
            return new CommandResult(String.format(MESSAGE_SHOW_FAILURE));
    	}
        return new CommandResult(String.format(getMessageForTaskListShownSummary(model.getFilteredTaskList().size())));
    }
}