package seedu.tasklist.logic.commands;

/**
 * Shows all tasks that fulfill the category keyword.
 * Keyword matching is case insensitive.
 */
public class ShowCommand extends Command {

    public static final String COMMAND_WORD = "find";

    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Shows all tasks under the requested category. "
            + "The specified keywords (case-insensitive) are shown as a list with index numbers.\n"
            + "Parameters: KEYWORD (all, completed, p/[PRIORITY]\n"
            + "Example: " + COMMAND_WORD + " all";

    public static final String MESSAGE_FIND_TASK_FAILURE = "No such task was found.";
    private final String keyword;
    
    public ShowCommand(String keyword) {
        this.keyword = keyword;
    }

    @Override
    public CommandResult execute() {
        if (keyword.equalsIgnoreCase("all")) {
            model.updateFilteredListToShowAll();
        }
        else if (keyword.equalsIgnoreCase("completed")) {
            model.updateFilteredListToShowComplete();
        }
    	
    	return null;
    }
}