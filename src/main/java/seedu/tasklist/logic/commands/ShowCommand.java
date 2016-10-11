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
    	    model.updateFilteredListToShowAll();
    	    return new CommandResult(String.format(getMessageForPersonListShownSummary(model.getFilteredPersonList().size())));
    	    
        case "incomplete":
            model.updateFilteredListToShowIncomplete();
            return new CommandResult(String.format(getMessageForPersonListShownSummary(model.getFilteredPersonList().size())));
    		
        case "complete":
            model.updateFilteredListToShowComplete();
            return new CommandResult(String.format(getMessageForPersonListShownSummary(model.getFilteredPersonList().size())));
    		
        case "p/high": case "p/med": case "p/low":
            model.updateFilteredListToShowPriority(keyword);
            return new CommandResult(String.format(getMessageForPersonListShownSummary(model.getFilteredPersonList().size())));
    	
        default:
            return new CommandResult(String.format(MESSAGE_SHOW_FAILURE));
    	}
}